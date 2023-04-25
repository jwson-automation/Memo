# Retrofit

api : https://openweathermap.org/api

권장사항

- 레트로핏 인스턴스는 앱이 실행될 때 한번만 호출, 싱글톤으로 사용하는 걸 권장합니다.

추가설정

- enqueue()를 사용하면 비동기로 호출, execute()를 사용하면 동기로 호출

- 결과 : onResponce, onFailure

- 플러그인 `JSON To Kotlin Class`를 사용하면 Json을 자동으로 Data Class로 예쁘게 바꿔줍니다.

심화 지식

- onResponse와 onFailure이 나눠져 있는데 한번더 isSuccessful로 확인하는 이유는, Response와 Failure에서는 HTTP 통신 가능 여부만 확인하고, 통신된 정보의 성공 실패는 체크하지 않기 때문이다.

## 라이브러리 추가

```gradlew
    // 레트로핏
    // https://github.com/square/retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
```

## 1번만 생성 시키기 위해서 `Application`상속 받기

manifest에 보면 activity위에 `Application`으로 묶여있습니다.

그때 레트로핏을 만들면, 권장사항인 `1번만 생성 시키기`에 맞게 됩니다.

```kotlin
// 앱이 실행될 때 1번만 실행이 됨
class ApplicationClass : Application() {

    val WEATHER_URL = "https://api.openweathermap.org/data/2.5/"

    companion object {

        // 전역변수 문법을 통해 Retrofit 인스턴스를 앱 실행 시 1번만 생성하여 사용 (싱글톤 객체)
        lateinit var wRetrofit : Retrofit
    }

    override fun onCreate() {
        super.onCreate()

        // 앱이 처음 생성되는 순간, retrofit 인스턴스를 생성
        wRetrofit = Retrofit.Builder()
                .baseUrl(WEATHER_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

    }
}
```

## 인터페이스 생성

```kotlin
interface WeatherInterface {

    @GET("weather")
    fun getWeather(
        @Query("q") q: String,
        @Query("appid") appid: String
    ): Call<WeatherResponse>
}
```

## 액티비티에 적용

```kotlin
@SuppressLint("SetTextI18n")
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    val API_KEY = "5cbe9dd4041225479ef6d0e088b2ffb8"  //""OPEN WEATHER MAP API KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tempBtn.setOnClickListener {
            val city = binding.cityEt.text.toString()
            var result = getWeatherData(city, API_KEY)
        }
    }

    fun getWeatherData(city: String, key: String) {

        val weatherInterface = ApplicationClass.wRetrofit.create(WeatherInterface::class.java)
        weatherInterface.getWeather(city, key).enqueue(object : Callback<WeatherResponse> {
            override fun onResponse(
                call: Call<WeatherResponse>,
                response: Response<WeatherResponse>
            ) {
                Log.d(TAG, "onResponse: ${response.body()}")
                if (response.isSuccessful) {
                    val temp = response.body()?.main?.temp
                    binding.tempTv.text = temp.toString()
                    binding.textView.text = response.body().toString()


                } else {
                }
            }

            override fun onFailure(call: Call<WeatherResponse>, t: Throwable) {
                Log.d(TAG, "onFailure: ${t.message}")
            }
        })


    }
}
```
