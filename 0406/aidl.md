# AIDL

: `Android Interface Definition Language`
: `안드로이드 껍데기 정의 언어`

## 실습 AIDL BINDER 생성 ( getTime function )

1. 자바도 코틀린도 아닌 aidl 파일을 하나 만들어줍니다.

```aidl
interface IMyAidlInterface {
    String getCurrentTime();
    }
```

2. build 해주면 Stub()이 자동으로 생성됩니다.

3. Activity에서 구현해줍니다.

```
class AIDLService : Service() {

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate: ")
    }
    // AIDL로 정의해서 생성한 Stub() 구현
    // object 어나니머스 클래스 만들어주고
    private val mBinder = object : IMyAidlInterface.Stub(){
        override fun getCurrentTime(): String {
            return Date().toString()
        }
    }
    override fun onBind(p0: Intent?): IBinder {
        Log.d(TAG, "onBind: ")
        //위에서 생성한 binder 리턴.
        return mBinder
    }
}
```

4. manifest에서 외부 호출 권한을 설정해줍니다.

```manifest
<service android:name=".AIDLService"
            android:exported="true" />
```

5. 여기에서 생성된 `IMyAidlinterface`를 호출할 apk에 넣어줍니다.

6. 해당 Activity에서 불러줍니다.

```kotlin
class AIDLActivity : AppCompatActivity() {
    private var bound = false
    private var timeService: IMyAidlInterface? = null

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            Log.d(TAG, "onServiceConnected: $p1 ")
            //binder 를 asInterface로 casting.
            timeService = IMyAidlInterface.Stub.asInterface(p1)
            bound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: ")
            timeService = null
            bound = false
        }
    }

    override fun onStart() {
        super.onStart()
        if (!bound) {
            Log.d(TAG, "onStart: ")
            //Component Name 생성하고, bindService  호출.
            val target = ComponentName("com.ssafy.service.c_aidl_binder","com.ssafy.service.c_aidl_activity.AIDLService")
            val intent = Intent()
            intent.component = target
            bindService(intent,connection, BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        if (bound) {
            unbindService(connection)
            bound = false
        }
    }

    private lateinit var binding: ActivityAidlactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAidlactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.button2.setOnClickListener{
            val time = timeService?.getCurrentTime()?:"00:00"
            binding.textView2.text = "AIDL Service 호출. \n한국시간은 $time 입니다."
        }
    }
}
```

AIDL 링크
https://developer.android.com/guide/components/aidl?hl=ko#PassingObjects
