# Retrofit

api : https://openweathermap.org/api

권장사항

- 레트로핏 인스턴스는 앱이 실행될 때 한번만 호출, 싱글톤으로 사용하는 걸 권장합니다.

추가설정

- enqueue()를 사용하면 비동기로 호출, execute()를 사용하면 동기로 호출

- 결과 : onResponce, onFailure

- 플러그인 `JSON To Kotlin Class`를 사용하면 Json을 자동으로 Data Class로 예쁘게 바꿔줍니다.

-

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
