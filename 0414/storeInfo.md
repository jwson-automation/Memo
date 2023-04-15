# Place API

1. place API를 사용하기 위해 먼저 설정해줘야 합니다. ( 카드 등록 )

## 실습

서버통신

```kotlin
 private fun showPlaceInformation(location: Location) {
        mMap!!.clear() //지도 클리어
        exMarker.clear() //지역정보 마커 클리어

        NRPlaces.Builder()
            .listener(this)
            .key(resources.getString(R.string.api_key))
            .latlng(location.latitude, location.longitude) //현재 위치
            .radius(500) //500 미터 내에서 검색
            .type(searchType) //음식점
            .build()
            .execute()
    }
```

타입에 따라서 마커 바꿔주기

```kotlin
fun getMarker(searchType:String) : BitmapDescriptor{
        val marker: BitmapDescriptor
        //4. 마커 바꾸기
        when(searchType){
            PlaceType.RESTAURANT -> {
                val bitmapdraw = ResourcesCompat.getDrawable(resources, R.drawable.location_icon, theme) as BitmapDrawable
                val b = bitmapdraw.bitmap
                marker = BitmapDescriptorFactory.fromBitmap( Bitmap.createScaledBitmap(b, 100, 100, false))
            }
            PlaceType.BANK ->  {
                val bitmapdraw = ResourcesCompat.getDrawable(resources, R.drawable.money, theme) as BitmapDrawable
                val b = bitmapdraw.bitmap
                marker = BitmapDescriptorFactory.fromBitmap( Bitmap.createScaledBitmap(b, 100, 100, false))
            }
            PlaceType.CAFE -> {
                val bitmapdraw = ResourcesCompat.getDrawable(resources, R.drawable.cafe, theme) as BitmapDrawable
                val b = bitmapdraw.bitmap
                marker = BitmapDescriptorFactory.fromBitmap( Bitmap.createScaledBitmap(b, 100, 100, false))
            }
            else -> {
                marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            }
        }
        return marker
    }
```

전체 코드

```Kotlin
package com.ssafy.googlemap

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission
import noman.googleplaces.*
import java.io.IOException
import java.util.*

//1. 다른 전문점도 검색되도록 추가.
class PlacesAPIActivity2 : AppCompatActivity(), OnMapReadyCallback, PlacesListener {
    private val UPDATE_INTERVAL = 1000 // 1초
    private val FASTEST_UPDATE_INTERVAL = 500 // 0.5초

    private var mMap: GoogleMap? = null
    private var currentMarker: Marker? = null
    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var currentPosition: Location

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_places_location2)

        locationRequest = LocationRequest.create().apply {
            priority = Priority.PRIORITY_HIGH_ACCURACY
            interval = UPDATE_INTERVAL.toLong()
            smallestDisplacement = 10.0f
            fastestInterval = FASTEST_UPDATE_INTERVAL.toLong()
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        /** button Event 처리 **/
        val btnSearch:Button = findViewById(R.id.btnSearch)
        btnSearch.setOnClickListener {
            showPlaceInformation(currentPosition)
        }
    }

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0

        //퍼미션 요청 대화상자 (권한이 없을때) & 실행 시 초기 위치를 서울 중심부로 이동
        setDefaultLocation()

        if (checkPermission()) { // 1. 위치 퍼미션을 가지고 있는지 확인
            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식)
            startLocationUpdates() // 3. 위치 업데이트 시작
        } else {  //2. 권한이 없다면
            // 3-1. 사용자가 권한이 없는 경우에는
            val permissionListener = object : PermissionListener {
                // 권한 얻기에 성공했을 때 동작 처리
                override fun onPermissionGranted() {
                    startLocationUpdates()
                }
                // 권한 얻기에 실패했을 때 동작 처리
                override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                    Toast.makeText(this@PlacesAPIActivity2,
                        "위치 권한이 거부되었습니다.",
                        Toast.LENGTH_SHORT).show()
                }
            }
            TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setDeniedMessage("[설정] 에서 위치 접근 권한을 부여해야만 사용이 가능합니다.")
                // 필요한 권한 설정
                .setPermissions(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                .check()
        }

        if (mMap != null) mMap!!.uiSettings.isMyLocationButtonEnabled = true
    }

    //위치정보 요청시 호출
    var locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val locationList = locationResult.locations
            if (locationList.size > 0) {
                currentPosition = locationList[locationList.size - 1]

                //현재 위치에 마커 생성하고 이동
                setCurrentLocation(currentPosition)
            }
        }
    }

    private fun startLocationUpdates() {
        // 위치서비스 활성화 여부 check
        if (!checkLocationServicesStatus()) {
            showDialogForLocationServiceSetting()
        } else {
            if (checkPermission()) {
                mFusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    null
                )
                if (mMap != null) {
                    mMap!!.isMyLocationEnabled = true
                    mMap!!.uiSettings.isZoomControlsEnabled = true
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (checkPermission()) {
            startLocationUpdates()
        }
    }

    override fun onStop() {
        super.onStop()
        mFusedLocationClient.removeLocationUpdates(locationCallback)

    }

    fun setCurrentLocation(location:Location){
        val markerTitle: String = getCurrentAddress(location)
        val markerSnippet =
            "위도: ${location.latitude.toString()}, 경도: ${location.longitude }"

        //현재 위치에 마커 생성하고 이동
        setCurrentLocation(location, markerTitle, markerSnippet)
    }

    fun setCurrentLocation(location: Location, markerTitle: String?, markerSnippet: String?) {
        currentMarker?.remove()

        val currentLatLng = LatLng(location.latitude, location.longitude)

        val markerOptions = MarkerOptions()
        markerOptions.position(currentLatLng)
        markerOptions.title(markerTitle)
        markerOptions.snippet(markerSnippet)
        markerOptions.draggable(true)

        currentMarker = mMap!!.addMarker(markerOptions)
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f)
        mMap!!.animateCamera(cameraUpdate)
    }

    fun getCurrentAddress(latLng: LatLng):String{
        val location = Location("")
        location.latitude = latLng.latitude
        location.longitude = latLng.longitude
        return getCurrentAddress(location)
    }

    fun getCurrentAddress(location: Location): String {
        //지오코더: GPS를 주소로 변환
        val geocoder = Geocoder(this, Locale.getDefault())
        val addresses: List<Address>?
        try {
            addresses = geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1
            )
        } catch (ioException: IOException) {
            //네트워크 문제
            Toast.makeText(this, "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show()
            return "지오코더 사용불가"
        } catch (illegalArgumentException: IllegalArgumentException) {
            Toast.makeText(this, "잘못된 GPS 좌표", Toast.LENGTH_LONG).show()
            return "잘못된 GPS 좌표"
        }

        return if (addresses == null || addresses.isEmpty()) {
//            Toast.makeText(this, "주소 발견 불가", Toast.LENGTH_LONG).show()
            "주소 발견 불가"
        } else {
            val address = addresses[0]
            address.getAddressLine(0).toString()
        }
    }

    private fun setDefaultLocation() {
        //초기 위치를 서울로
        var location = Location("")
        location.latitude = 37.56
        location.longitude = 126.97

        val markerTitle = "위치정보 가져올 수 없음"
        val markerSnippet = "위치 퍼미션과 GPS 활성 여부 확인 필요"

        if(checkPermission()) {
            mFusedLocationClient.lastLocation.addOnSuccessListener { loc:Location? ->
                if(loc != null) location = loc
            }
        }

        setCurrentLocation(location, markerTitle, markerSnippet)
    }

    /** 권한 관련 **/
    private fun checkLocationServicesStatus(): Boolean {
        val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
    }

    private fun checkPermission(): Boolean {
        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        return hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED
    }

    /******** 위치서비스 활성화 여부 check *********/
    private val GPS_ENABLE_REQUEST_CODE = 2001
    private var needRequest = false

    private fun showDialogForLocationServiceSetting() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("위치 서비스 비활성화")
        builder.setMessage(
            "앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
        )
        builder.setCancelable(true)
        builder.setPositiveButton("설정") { _, _ ->
            val callGPSSettingIntent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE)
        }
        builder.setNegativeButton("취소"
        ) { dialog, _ -> dialog.cancel() }
        builder.create().show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            GPS_ENABLE_REQUEST_CODE ->
                //사용자가 GPS를 켰는지 검사함
                if (checkLocationServicesStatus()) {
                    needRequest = true
                    return
                }else{
                    Toast.makeText(this@PlacesAPIActivity2,
                        "위치 서비스가 꺼져 있어, 현재 위치를 확인할 수 없습니다.",
                        Toast.LENGTH_SHORT).show()
                }
        }
    }

    /** places 검색 추가.**/
    private var exMarker = mutableSetOf<MarkerOptions>()

    override fun onPlacesFailure(e: PlacesException?) {
    }

    override fun onPlacesStart() {
    }

    override fun onPlacesFinished() {
    }
    override fun onPlacesSuccess(places: List<Place>?) {
        for (place in places!!) {
            val latLng = LatLng(
                place.latitude, place.longitude
            )

            val markerSnippet = getCurrentAddress(latLng)
            val markerOptions = MarkerOptions()
            markerOptions.position(latLng)
            markerOptions.title(place.name)
            markerOptions.icon(getMarker(searchType))
            markerOptions.snippet(markerSnippet)
            exMarker.add(markerOptions)
        }

        //UI Thread에서 추가.
        runOnUiThread{
            exMarker.forEach {
                mMap!!.addMarker(it)
            }
        }

    }


    fun getMarker(searchType:String) : BitmapDescriptor{
        val marker: BitmapDescriptor
        //4. 마커 바꾸기
        when(searchType){
            PlaceType.RESTAURANT -> {
                val bitmapdraw = ResourcesCompat.getDrawable(resources, R.drawable.location_icon, theme) as BitmapDrawable
                val b = bitmapdraw.bitmap
                marker = BitmapDescriptorFactory.fromBitmap( Bitmap.createScaledBitmap(b, 100, 100, false))
            }
            PlaceType.BANK ->  {
                val bitmapdraw = ResourcesCompat.getDrawable(resources, R.drawable.money, theme) as BitmapDrawable
                val b = bitmapdraw.bitmap
                marker = BitmapDescriptorFactory.fromBitmap( Bitmap.createScaledBitmap(b, 100, 100, false))
            }
            PlaceType.CAFE -> {
                val bitmapdraw = ResourcesCompat.getDrawable(resources, R.drawable.cafe, theme) as BitmapDrawable
                val b = bitmapdraw.bitmap
                marker = BitmapDescriptorFactory.fromBitmap( Bitmap.createScaledBitmap(b, 100, 100, false))
            }
            else -> {
                marker = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            }
        }
        return marker
    }

    private var searchType = PlaceType.RESTAURANT
    private fun showPlaceInformation(location: Location) {
        mMap!!.clear() //지도 클리어
        exMarker.clear() //지역정보 마커 클리어

        NRPlaces.Builder()
            .listener(this)
            .key(resources.getString(R.string.api_key))
            .latlng(location.latitude, location.longitude) //현재 위치
            .radius(500) //500 미터 내에서 검색
            .type(searchType) //음식점
            .build()
            .execute()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        findViewById<TextView>(R.id.textView).text = item.title
        when(item.itemId){
            R.id.menu_RESTAURANT -> searchType = PlaceType.RESTAURANT
            R.id.menu_BANK -> searchType = PlaceType.BANK
            R.id.menu_CAFE -> searchType = PlaceType.CAFE
        }

        return super.onOptionsItemSelected(item)
    }

}
```
