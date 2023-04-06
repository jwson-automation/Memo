```
어제 복습

1. 액티비티, 인텐트

2. 암시적, 명시적 인텐트 ( 암시적이 어렵습니다. )

3. Task 관리 ( 런치모드 / 플래그 )

4. 실습 : 리사이클러뷰 + CRUD(오브젝트클래스) +
```

# Service

bindService - 어려운 친구
foregroundService - 예전에 알람때 해봤던 친구
Notification - 양식대로만 만들어 주면 되는 친구

목차
[bind Service](#bind-service)

## bind Service

아래처럼 서비스는 액티비티에게 데이터를 전달해줄 필요가 없다. 별개의 프로그램으로써 보이지 않는 영역에서 실행될 뿐이다.

하지만, 바인드 서비스는 액티비티와 Request, Response를 주고 받는다.

```
[service]
startService() > onCreate() > onStartCommand() > ServiceRunning > onDestroy()

[bindService]
bindService() > onCreate() > onBind() > BindRunning > onUnbind() > onDestroy
```

- 안드로이드 플랫폼 아키텍쳐의 가장 하단에
  `Linux Kernel`에 `Binder(IPC)`라는 시스템이 있는데, 그 부분과 관련이 있다.

### bindService 구현 방법

1. Local
   : `하나의 apk 안에 들어가 있는 것`

   `Q` : 액티비티 쓰지 서비스 왜 필요함?
   `A` : DB도 그냥 Activity에서 바로 Access하면 됩니다. 하지만,
   액티비티는 보여주기 위한 영역입니다.

   그러니까 DB는 백그라운드에 넣어주는게 더 구조적으로 분류가 더 잘되어 있는 것으로 보입니다.

   이렇게 하면 어제 실습에서 했던 것 처럼 object Class에서 DB를 만들어 두면 데이터가 여러 액티비티에서 자유롭게 처리할 수 있는 것 처럼

   아름다운 구조가 됩니다.

2. Local x
   : `apk가 다르다는 말입니다.`

   ```
   `Q` : 왜 apk 따로 분리함?
   `A` : 분리하는게 아니라, 다른 apk 사이에서의 통신을 도와주는 용도로 만드는 거임
   ```

   이게 특이한게 아니라, 안드로이드 플랫폼에서 다른 apk 사이의 통신은 꽤 많이 사용됩니다.

   앱 -> 지도
   문자 -> 뱅킹앱 , 등 ...

   - Messenger , Handler를 사용한다.
   - AIDL (Android Interface Definition Language) 를 이용하여 서비스 구현

   ```
   AIDL 인터페이스 정의언어란?
   VM이 다르다고 해도, C나 Java 둘다 결국 String, Int 쓸텐데 데이터 왔다 갔다 할때마다 내가 다 매번 해줘야 하는게 말이 됨?!
   어차피 데이터 타입은 정해져 있고, 네트워크 코드 함짜두면 계속 쓸 수 있을텐데... --> 그래서 `데이터 교환을 위한 네트워크 코드를 정의해둔 인터페이스`가 `AIDL` 입니다.
   ```

   오후에 AIDL을 만들어 볼 예정입니다.
   안드로이드 코드서치에 들어가서, 프레임워크의 코드를 열어보면 aidl이 참 많이 들어있습니다. 이런 aidl 통신을 하는게 `binder` 드라이버 입니다. ( 통신이라고 하지만, 네트워크로 주고 받는게 아닌 안드로이드 내부 리눅스 안에서 데이터를 주고 받는 것을 말함)

   여기서 핵심!

   ```
   apk가 2개가 있다고 했을 때, 서버를 이용하거나 네트워크(인터넷)을 사용해서 정보를 교환시키는 것보다는, 리눅스의 자체 커널 `바인더`를 사용해서 통신을 시키는게 퍼포먼스 적으로 훨씬 이득이다.
   ```

   따라서,

   ```
   바인드 서비스라는 걸 이용하는 이유는, 구조적으로 예쁠뿐만 아니라, 퍼포먼스 적으로 네트워크를 사용하지 않고 다른 VM끼리 통신을 가능하게 만들어서 효율적이다!
   ```

   여기에서 사용하는게 `Parcelable` 입니다.
   (안드로이드는 Parcel을 사용하는 것을 권장합니다. 그래서 맨날 추천에 뜨나봄)

### bindService 프로세스

```
Activity
> ServiceConnection  --> [bindService(Service Connection)] -->
> Service
> Binder  --> [ServiceConnection.onServiceconnected(Binder)] -->
> Service Connection
```

### 샘플코드

```Kotlin
class BoundService : Service() {

    // 기본적으로 생성됨
    override fun onBind(p0: Intent?): IBinder? {
        return MyLocalBinder()
    }

    fun getCurrentTime() : String {
        return Date().toString()
    }

    // 이너를 붙여줘야 바깥에 엑세스 가능합니다.
    // 액티비티가 나를 호출할거고, 나는 호출 당한 후에 Binder를 리턴해주고
    // 리턴값을 받은 액티비티는 getCurrentTime을 이용해서 값을 얻을 수 있다.
    inner class MyLocalBinder() : Binder() {
        fun getService() : BoundService {
            return this@BoundService
        }
    }

}
```

```Kotlin
class BindActivity : AppCompatActivity() {
    lateinit var myService : BoundService
    private var isBound = false

    // ServiceConnection이라는 인터페이스를 가져오기 위해서는
    // 앞에 Object를 붙여주면 된다. ( 그렇게 쓴다. )
    val connection = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            val binder = p1 as BoundService.MyLocalBinder
            myService = binder.getService()
            isBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            isBound = false
        }

    }

    private lateinit var binding : ActivityBindBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBindBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.button.setOnClickListener(){
            if(isBound){
            binding.textView.text = myService.getCurrentTime()
            }else{
                binding.textView.text = "not Connected"
            }
        }
    }

    override fun onStart() {
        super.onStart()
        // 바인딩 할겁니다
        val intent = Intent(this, BoundService::class.java)
        bindService(intent, connection, BIND_AUTO_CREATE)
    }

}
```

### 그외

`시리얼라이즈블` vs `Parcelable`

IBinder,, I는 인터페이스다...
