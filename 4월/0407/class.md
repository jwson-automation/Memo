# 0407

## 어제 과제 피드백 ( 교수님께 질문 )

서비스를 액티비티에서 각각 부르는 것도 문제가 없다.
( 하지만 코드가 중복된다! )

따로 불렀을때 문제가 발생하지 않는다면 그렇다!

## 관통프로젝트

- 아직 배우지 않았지만 Fragment를 배울 예정이고,

- Fragment 통신 코드도 별도로 추가해야 합니다.

- 그래서 이번에는 Activity 단위로만 생성할 예정입니다.

-

* adb.exe 파일은 배포할 때 사용되는 가장 많은 기능을 가진 친구다.
  가끔 문제가 발생하면 adb.exe파일을 프로세스에서 종료해주고 다시하면 잘된다.

## 키보드 이슈 해결 방법

constraintLayout 상단에 스크롤뷰를 추가해주면 키보드가 제공된 뒤 화면 스크롤이 가능해진다.

```xml
<ScrollView  xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent">
```

또는 아래와 같이 edit Text에 스크롤바를 만들어 주는 것으로 해결 가능하다. ( 사진 참고 )

```xml
<editText
scrollbars = "verticle"
/>
```

## 로그인으로 뒤로가기 못하게 막는 방법

FLAG를 이용해서 CLEAR, NEW를 해주면 해당 액티비티가 스택에서 빠진다.

```kotlin
loginBtn.setOnClickListener(){
            var intent = Intent(this,MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
```

##
