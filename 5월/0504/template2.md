# template 2

## 코드

1. 스플래쉬 만들기

- 만들고 메니페스트에서 어플리케이션과 함께 호출한다.

```kotlin
class SplashActivity : BaseActivity<ActivitySplashBinding>(ActivitySplashBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 1500)
    }
}
```

```manifest
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    package="com.ssafy.template">

    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".config.ApplicationClass"
        android:allowBackup="false"
        android:icon="@mipmap/logo_ssafy"
        android:label="@string/app_name"
        android:networkSecurityConfig="@xml/network_security_config"
        android:supportsRtl="true"
        android:theme="@style/AppTheme"
        tools:ignore="AllowBackup">
        <activity
            android:name=".src.splash.SplashActivity"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme.NoActionBar" android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />

                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        <activity
            android:name=".src.main.MainActivity"
            android:screenOrientation="portrait"
            android:theme="@style/AppTheme.NoActionBar" />
    </application>

</manifest>
```

2. Response도 미리 빼놔서 활용해서 사용합니다.

```kotlin
// 반복되는 리스폰스 내용 중복을 줄이기 위해 사용. 리스폰스 데이터 클래스를 만들때 상속해서 사용합니다.
open class BaseResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean = false,
    @SerializedName("code") val code: Int = 0,
    @SerializedName("message") val message: String? = null
)
```

```kotlin
data class UserResponse(
    @SerializedName("result") val result: ArrayList<ResultUser>
) : BaseResponse()
```

```kotlin
data class SignUpResponse(
    @SerializedName("result") val result: ResultUser
) : BaseResponse()
```
