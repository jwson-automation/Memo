1. Build가 제대로 안됨

File > Project Structure > 값 확인

Build Version 확인 > Build.gradle(project)

```
4.2.1 이전 버전인 경우 문제가 자주 발생한다.

dependencies {
        classpath 'com.android.tools.build:gradle:4.2.1'
    }

```

2. 외부에서 가져온 의존성이 주입이 안됨

Settings.gradle >

```
google에서 가져오는 것과 mavenCentral에서 가져오는 것
그리고 jcenter에서 가져오는 것이 있다.

외부에서 뭔가를 가져오기 전에 어디서 가져오는 것이 맞는지 확인하고

레포를 추가해줘야함

repositories {
        google()
        mavenCentral()
        jcenter()
    }
}
```

3. 외부 라이브러리

https://meoru-tech.tistory.com/22

gradle.properties

```
android.useAndroidX=true
android.enableJetifier=true
```