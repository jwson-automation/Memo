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
