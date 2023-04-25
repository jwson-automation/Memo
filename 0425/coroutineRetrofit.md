# Coroutine + Retrofit

코루틴을 추가하면 생기는 차이

- 인터페이스 메소드에 suspend가 추가되어야 합니다. ( 딜레이 가능 )

- 의무적으로 Call<> 타입으로 받던 것을 실제로 필요한 `WeatherResponse`를 바로 받아도 상관 없습니다.

- `launch`를 쓰면 끝나고 사라지고, `withContext`는 될 때까지 기다려줍니다.

## 라이브러리 추가

```gradlew
    //coroutine
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2'

    // 레트로핏
    // https://github.com/square/retrofit
    implementation 'com.squareup.retrofit2:retrofit:2.9.0'
```

## 인터페이스 코드

```kotlin

interface WeatherInterface {

    @GET("weather")
    suspend fun getWeather(
        @Query("q") q: String,
        @Query("appid") appid: String
    ): Response<WeatherResponse>
}
```

## 액티비티 코드

```kotlin
fun getWeatherData(city: String, key: String) {

        val weatherInterface = ApplicationClass.wRetrofit.create(WeatherInterface::class.java)

        CoroutineScope(Dispatchers.Main).launch {
            val response = weatherInterface.getWeather(city,key)

            if(response.isSuccessful){
                val temp = response.body()?.main?.temp
                    binding.tempTv.text = temp.toString()
                    binding.textView.text = response.body().toString()
            }
        }
```
