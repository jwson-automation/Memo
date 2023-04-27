# retrofit REST API

## 게시판 만들기

## Retrofit 설정

```gradlew
    //coroutine
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2'

    // 레트로핏
    // https://github.com/square/retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'

    // https://github.com/square/retrofit/tree/master/retrofit-converters/gson
    implementation 'com.squareup.retrofit2:converter-gson:2.9.0'
```

## Manifest 설정

```manifest
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.ssafy.network_2.board">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".ApplicationClass"
        android:allowBackup="true"
        android:icon="@mipmap/ic_launcher"
        android:label="@string/app_name"
        android:networkSecurityConfig="@xml/network_security_config"
        android:roundIcon="@mipmap/ic_launcher_round"
        android:supportsRtl="true"
        android:theme="@style/Theme.Network_http"
        tools:targetApi="n">
        <activity android:name=".WriteActivity"/>
        <activity android:name=".DetailActivity" />
        <activity android:name=".MainActivity" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>

</manifest>
```

## 인터페이스 레트로핏

```kotlin
interface BoardService {


    @GET("api/board")
    fun selectAll(): Call<MutableList<Board>>

    @GET("api/board/{no}")
    fun selectBoard(@Path("no") no:String): Call<Board>

    @DELETE("api/board/{no}")
    fun deleteBoard(@Path("no") no:String): Call<Unit>

    @PUT("api/board/{no}")
    fun updateBoard(@Path("no") no:String, @Body board:Board): Call<Unit>

    @POST("api/board")
    fun insertBoard(@Body board:Board): Call<Unit>
}
```

## 레트로핏 어플리케이션

```kotlin
// 앱이 실행될 때 1번만 실행이 됨
class ApplicationClass : Application() {

    //url must be end with "/"
    val BOARD_URL   = "http://192.168.33.33:3306/vue/"

    companion object {
        // 전역변수 문법을 통해 Retrofit 인스턴스를 앱 실행 시 1번만 생성하여 사용 (싱글톤 객체)
        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        retrofit = Retrofit.Builder()
                .baseUrl(BOARD_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
    }
}
```

## 액티비티 연결

```kotlin
class WriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityWriteBinding

    private var no = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        no = intent.getStringExtra("no")?.toInt() ?: -1 // 수정일경우 넘어옴.

        if( no >= 0 ) {
            getData()
        }

        initEvent()
    }

    private fun initEvent(){
        binding.buttonSave.setOnClickListener{
            if(no >= 0){
                update()
            }else{
                save()
            }
        }

        binding.buttonInit.setOnClickListener{
            binding.title.setText("")
            binding.content.setText("")
        }

        binding.buttonClose.setOnClickListener{
            finish()
        }
    }

    private fun getData(){
        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.selectBoard(no.toString()).enqueue( object : Callback<Board>{
            override fun onResponse(call: Call<Board>, response: Response<Board>) {
                if(response.isSuccessful){
                    initView(response.body() as Board)
                }else{
                    Log.d(TAG, "selectBoard - onResponse : Error code ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Board>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }

        })
    }

    private fun initView(board: Board){
        //수정으로 제목변경
        binding.textViewTitle.text = "게시글 수정"

        binding.no.text = board.no.toString()
        binding.regTime.text = board.regtime.toString()

        //editText는 setText로 assign
        binding.writer.setText(board.writer)
        binding.title.setText(board.title.toString())
        binding.content.setText(board.content.toString())
    }

    fun save(){
        var board = Board().apply{
            writer = binding.writer.text.toString()
            title = binding.title.text.toString()
            content = binding.content.text.toString()
        }

        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.insertBoard(board).enqueue(object:Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@WriteActivity, "저장하였습니다.", Toast.LENGTH_SHORT).show()
                    finish()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }
        })
    }

    fun update(){
        var board = Board().apply{
            writer = binding.writer.text.toString()
            title = binding.title.text.toString()
            content = binding.content.text.toString()
        }

        val service = ApplicationClass.retrofit.create(BoardService::class.java)
        service.updateBoard(no.toString(), board).enqueue(object:Callback<Unit>{
            override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                if(response.isSuccessful){
                    Toast.makeText(this@WriteActivity, "수정하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Unit>, t: Throwable) {
                Log.d(TAG, t.message ?: "통신오류")
            }
        })
    }


}
```
