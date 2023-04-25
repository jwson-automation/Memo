# NFC 실습

## 권한설정

`<uses-permission android:name="android.premission.NFC">`를 manifest에 넣는다.

## manifest

```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.ssafy.android.tag_recognition">

    <uses-permission android:name="android.permission.NFC" />

    <application
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.1_nfc">
        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
            <intent-filter>
                <action android:name="android.nfc.action.NDEF_DISCOVERED"/>
                <category android:name="android.intent.category.DEFAULT"/>
                <data android:mimeType="text/plain"/>
            </intent-filter>

        </activity>
    </application>

</manifest>
```

## 액티비티

```kotlin

/**
 * activity의 default launchMode : standard이므로 onCreate가 계속 호출된다.
 * 호출시마다 Activity를 재생성
 */
private const val TAG = "MainActivity_싸피"

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    @Override
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val action = intent.action
        binding.textview.text = action
        Toast.makeText(this, "onCreate$action", Toast.LENGTH_SHORT).show()
        Log.d(TAG, "onCreate: $action")

        if (action == NfcAdapter.ACTION_NDEF_DISCOVERED){
        val data = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
        val message = data?.get(0) as NdefMessage
        val record = message.records[0] as NdefRecord
        val byreArr = record.payload

        Log.d(TAG, "onCreate: ${String(byreArr)}")
        }
    }

    @Override
    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show()
    }

}
```
