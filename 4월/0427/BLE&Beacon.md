# BLE

## 0. 메니페스트

```manifest
    <uses-permission android:name="android.permission.BLUETOOTH" android:maxSdkVersion="30" />
    <uses-permission android:name="android.permission.BLUETOOTH_ADMIN" android:maxSdkVersion="30" />
    <uses-permission android:name="android.permission.BLUETOOTH_SCAN"/>
    <uses-permission android:name="android.permission.BLUETOOTH_ADVERTISE" />
    <uses-permission android:name="android.permission.BLUETOOTH_CONNECT" />
```

## 1. 퍼미션 확인

```kotlin
class CheckPermission(private val context: Context) {
    fun runtimeCheckPermission(context: Context?, vararg permissions: String?): Boolean {
        if (context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission( context, permission!! ) != PackageManager.PERMISSION_GRANTED ) {
                    return false
                }
            }
        }
        return true
    }

    fun requestPermission() {
        val alertDialog = AlertDialog.Builder(
            context
        )
        alertDialog.setTitle("권한이 필요합니다.")
        alertDialog.setMessage("설정으로 이동합니다.")
        alertDialog.setPositiveButton("확인") { dialogInterface, i -> // 안드로이드 버전에 따라 다를 수 있음.
            val intent =
                Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).setData(Uri.parse("package:" + context.packageName))
            context.startActivity(intent)
            dialogInterface.cancel()
        }
        alertDialog.setNegativeButton("취소") { dialogInterface, i -> dialogInterface.cancel() }
        alertDialog.show()
    }
}
```

## 2. 액티비티 생성

```kotlin
@SuppressLint("MissingPermission")
@RequiresApi(api = Build.VERSION_CODES.M)
class MainActivity : AppCompatActivity() {
    companion object {
        private const val PERMISSION_REQUEST_CODE = 8
    }
    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var scanner: BluetoothLeScanner
    private lateinit var decideListAdapter: LeDeviceListAdapter
    private lateinit var checkPermission: CheckPermission

    private var blueAdapter: BluetoothAdapter? = null

    private val runtimePermissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.BLUETOOTH_SCAN,
        Manifest.permission.BLUETOOTH_ADVERTISE,
        Manifest.permission.BLUETOOTH_CONNECT
    )

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
        checkPermission = CheckPermission(this)
        blueAdapter = bluetoothManager.adapter

        if (blueAdapter == null || !blueAdapter!!.isEnabled) {
            Toast.makeText(this, "블루투스 기능을 확인해 주세요.", Toast.LENGTH_SHORT).show()
            val bleIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            startActivityForResult(bleIntent, 1)
        }
        scanner = blueAdapter!!.getBluetoothLeScanner()

        if (!checkPermission.runtimeCheckPermission(this, *runtimePermissions)) {
            ActivityCompat.requestPermissions(this, runtimePermissions, PERMISSION_REQUEST_CODE)
        } else { //이미 전체 권한이 있는 경우
            initView()
        }
    }

    override fun onRequestPermissionsResult( requestCode: Int, permissions: Array<String>, grantResults: IntArray  ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISSION_REQUEST_CODE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { // 권한을 모두 획득했다면.
                initView()
            } else {
                checkPermission.requestPermission()
            }
        }
    }

    private fun initView() {
        decideListAdapter = LeDeviceListAdapter(this)
        binding.devicelist.adapter = decideListAdapter
        binding.devicelist.onItemClickListener =
            OnItemClickListener { parent, view, position, id ->
                val device = decideListAdapter.getItem(position) as BluetoothDevice
                Log.d(TAG, "++++++++++++ Selected Device ++++++++++++++++++")
                Toast.makeText(this@MainActivity, "selected..$device", Toast.LENGTH_SHORT).show()
                Log.d(TAG, "onItemClick: $device") //62:A6:2D:32:69:F2
//				onScanResult: result:ScanResult{device=62:A6:2D:32:69:F2, scanRecord=ScanRecord [mAdvertiseFlags=4, mServiceUuids=[0000fd5a-0000-1000-8000-00805f9b34fb], mServiceSolicitationUuids=[], mManufacturerSpecificData={}, mServiceData={0000fd5a-0000-1000-8000-00805f9b34fb=[21, -22, 3, 1, 70, -20, -122, -22, 126, 44, 105, 77, -61, 0, 0, 0, -97, 25, -34, -7]}, mTxPowerLevel=-2147483648, mDeviceName=Smart Tag, mTransportBlocks=[]], rssi=-85, timestampNanos=2925531664814018, eventType=27, primaryPhy=1, secondaryPhy=0, advertisingSid=255, txPower=127, periodicAdvertisingInterval=0}
            }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_scan ->
                // Toast.makeText(this, "Start Scan", Toast.LENGTH_SHORT).show();
                startScan()
            R.id.action_stop ->
                // Toast.makeText(this, "Stop Scan", Toast.LENGTH_SHORT).show();
                stopScan()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun startScan() {
        decideListAdapter.clear()
        decideListAdapter.notifyDataSetChanged()
        Log.d(TAG, "startScan")
        // BLE Sensor Scan
        //전체 scan.
        scanner.startScan(scanCallback)

        //10초 후 scan 중지

    }

    private fun stopScan() {
        Log.d(TAG, "stopSCan")
        // BLE Sensor Scan Stop
        scanner.stopScan(scanCallback)
    }

    private var scanCallback: ScanCallback = object : ScanCallback() {
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            processResult(result)
        }

        override fun onScanFailed(errorCode: Int) {
            Log.d(TAG, "errorCode: errorCode:$errorCode")
        }

        private fun processResult(result: ScanResult) {
            Log.d(TAG, "processResult: ")
            Log.d(TAG, "++++++++++++scan Result++++++++++++++++++")
            Log.d(TAG, result.toString())
            runOnUiThread {
                decideListAdapter.addDevice(result.device)
                decideListAdapter.notifyDataSetChanged()
            }

        }
    }

    var handler = Handler(Looper.getMainLooper())

    override fun onPause() {
        super.onPause()
        stopScan()
    }

}
```

## 3. Adapter

```kotlin
@SuppressLint("MissingPermission")
class LeDeviceListAdapter(context: Context?) : BaseAdapter() {
    private val mLeDevices = mutableListOf<BluetoothDevice>()
    private val mInflator: LayoutInflater

    init {
        mInflator = LayoutInflater.from(context)
    }

    fun addDevice(device: BluetoothDevice) {
        if (!mLeDevices.contains(device)) {
            mLeDevices.add(device)
        }
    }

    fun getDevice(position: Int): BluetoothDevice {
        return mLeDevices[position]
    }

    override fun getView(i: Int, convertView: View?, viewGroup: ViewGroup): View {
        var view = convertView
        val viewHolder: ViewHolder
        // General ListView optimization code.
        if (view == null) {
            view = mInflator.inflate(R.layout.devicelistitem, null)
            viewHolder = ViewHolder()
            viewHolder.deviceAddress = view.findViewById(R.id.device_address)
            viewHolder.deviceName = view.findViewById(R.id.device_name)
            view.tag = viewHolder
        } else {
            viewHolder = view.tag as ViewHolder
        }
        val device = mLeDevices[i]

        viewHolder.deviceName.text = device.name ?: "unknown_device"
        viewHolder.deviceAddress.text = device.address ?: "unknown_address"

        return view!!
    }

    internal class ViewHolder {
        lateinit var deviceName: TextView
        lateinit var deviceAddress: TextView
    }

    fun clear() {
        mLeDevices.clear()
    }

    override fun getCount(): Int {
        return mLeDevices.size
    }

    override fun getItem(i: Int): Any {
        return mLeDevices[i]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

}
```

## 4. Conversion

```kotlin
object Conversion {
    fun loUint16(v: Short): Byte {
        return (v and 0xFF).toByte()
    }

    fun hiUint16(v: Short): Byte {
        return (v.toInt() shr 8).toByte()
    }

    fun buildUint16(hi: Byte, lo: Byte): Short {
        return ((hi.toInt() shl 8) + (lo.toInt() and 0xff)).toShort()
    }

    fun BytetohexString(b: ByteArray, len: Int): String {
        val sb = StringBuilder(b.size * (2 + 1))
        val formatter = Formatter(sb)
        for (i in 0 until len) {
            if (i < len - 1) formatter.format("%02X:", b[i]) else formatter.format("%02X", b[i])
        }
        formatter.close()
        return sb.toString()
    }

    fun BytetohexString(b: ByteArray, reverse: Boolean): String {
        val sb = StringBuilder(b.size * (2 + 1))
        val formatter = Formatter(sb)
        if (!reverse) {
            for (i in b.indices) {
                if (i < b.size - 1) formatter.format("%02X:", b[i]) else formatter.format(
                    "%02X",
                    b[i]
                )
            }
        } else {
            for (i in b.size - 1 downTo 0) {
                if (i > 0) formatter.format("%02X:", b[i]) else formatter.format("%02X", b[i])
            }
        }
        formatter.close()
        return sb.toString()
    }

    // Convert hex String to Byte
    fun hexStringtoByte(sb: String?, results: ByteArray): Int {
        var i = 0
        var j = false
        if (sb != null) {
            for (k in 0 until sb.length) {
                if (sb[k] >= '0' && sb[k] <= '9' || sb[k] >= 'a' && sb[k] <= 'f'
                    || sb[k] >= 'A' && sb[k] <= 'F'
                ) {
                    if (j) {
                        results[i] = (results[i] + sb[k].digitToInt(16).toByte()).toByte()
                        i++
                    } else {
                        results[i] = (sb[k].digitToInt(16).shl(4)).toByte()
                    }
                    j = !j
                }
            }
        }
        return i
    }

    fun isAsciiPrintable(str: String?): Boolean {
        if (str == null) {
            return false
        }
        val sz = str.length
        for (i in 0 until sz) {
            if (isAsciiPrintable(str[i]) == false) {
                return false
            }
        }
        return true
    }

    private fun isAsciiPrintable(ch: Char): Boolean {
        return ch.code >= 32 && ch.code < 127
    }
}
```
