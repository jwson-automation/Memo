# Google Map

구글 지도도 중요하지만 위치 정보를 가져오는 것이 중요합니다.

## 위치 센서

2가지 방법이 있습니다.

1. GPS Provider : 위성으로부터 좌표값을 얻어오기
2. Network Provider : 이동통신국, wifi 등을 이용해서 위치 추정

```
지구 궤도에 있는 인공위성의 개수는 14,710개. 이중 실제로 임무를 하는 인공위성은 6,900개
```

위 2기술을 이용한 2가지 선택이 또 가능합니다.

3. Passive : 다른 앱/서비스에서 사용한 위치정보를 가져옴
4. Fused : 여러 다른 Provider의 정보를 결합하여 최상의 위치를 제공 ( 마 최선을 다해 주이소 라는 뜻임 )

- 먼저 퍼미션을 가져옵니다.
  ( 인터넷(식당정보), 정확한 위치정보, 대략적인 위치정보 )

```manifest
<uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
```

- API KEY를 등록합니다.

```
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="@string/api_key" />
```

- `LocationManager`를 가져옵니다.

```kotlin
private val locationManager by lazy {
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
```

- 권한 요청 코드 제작 ( 퍼미션 리스터 라이브러리 )

```Kotlin
private fun checkPermission() {
        val permissionListener = object : PermissionListener {
            // 권한 얻기에 성공했을 때 동작 처리
            override fun onPermissionGranted() {
                initView()
            }
            // 권한 얻기에 실패했을 때 동작 처리
            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                Toast.makeText(this@GpsNetworkActivity, "위치 권한이 거부되었습니다.", Toast.LENGTH_SHORT).show()
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
```

- 최근 좌표값을 Last Location으로 잡습니다.
  `첫 화면을 미국이나, 서울로 보여주는 것보다 이전 위치를 보여주는 것이 더 자연스럽고 퍼포먼스 적으로도 좋아보입니다.`

```kotlin
 @SuppressLint("MissingPermission", "SetTextI18n")
    private fun setLastLocation() {
        //GPS provider
        var lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (lastKnownLocation != null) {
            binding.tvGpsLatitude.text = ":: ${lastKnownLocation.latitude}"
            binding.tvGpsLongitude.text = ":: ${lastKnownLocation.longitude}"
            Log.d(TAG, "latitude=${lastKnownLocation.latitude}, longitude=${lastKnownLocation.longitude}")
        }
        //network provider
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (lastKnownLocation != null) {
            binding.tvNetworkLatitude.text = ":: ${lastKnownLocation.latitude}"
            binding.tvNetworkLongitude.text = ":: ${lastKnownLocation.longitude}"
            Log.d(TAG, "latitude=${lastKnownLocation.latitude}, longitude=${lastKnownLocation.longitude}")
        }
        //passive provider
        lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER)
        if (lastKnownLocation != null) {
            binding.tvPassiveLatitude.text = ":: ${lastKnownLocation.latitude}"
            binding.tvPassiveLongitude.text = ":: ${lastKnownLocation.longitude}"
            Log.d(TAG, "latitude=${lastKnownLocation.latitude}, longitude=${lastKnownLocation.longitude}")
        }
    }
```

- 위치값을 가져옵니다.
  `requestLocationUpdates`를 이용해서 값을 업데이트 해줍니다.

```kotlin
@SuppressLint("MissingPermission")
    private fun getProviders(){
        val listProviders = locationManager.allProviders as MutableList<String>
        val isEnable = BooleanArray(4)
        for (provider in listProviders) {
            when ( provider ) {
                LocationManager.GPS_PROVIDER -> {
                    isEnable[0] = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    binding.tvGpsEnable.text = ": " + isEnable[0].toString()
                    locationManager.requestLocationUpdates(
                        LocationManager.GPS_PROVIDER,
                        0,
                        0f,
                        listener
                    )
                    Log.d(TAG, provider + '/' + isEnable[0].toString())
                }
                LocationManager.NETWORK_PROVIDER -> {
                    isEnable[1] = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                    binding.tvNetworkEnable.text = ": " + isEnable[1].toString()
                    locationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        0,
                        0f,
                        listener
                    )
                    Log.d(TAG, provider + '/' + isEnable[1].toString())
                }
                LocationManager.PASSIVE_PROVIDER -> {
                    isEnable[2] = locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER)
                    binding.tvPassiveEnable.text = ": " + isEnable[2].toString()
                    locationManager.requestLocationUpdates(
                        LocationManager.PASSIVE_PROVIDER,
                        0,
                        0f,
                        listener
                    )
                    Log.d(TAG, provider + '/' + isEnable[2].toString())
                }
            }
        }
    }
```

### 기타

- 시뮬레이터와 테스트폰으로 동시에 테스트하면 테스트폰은 계속 좌표가 변화되는데, 시뮬레이터는 멈춰있다.

-
