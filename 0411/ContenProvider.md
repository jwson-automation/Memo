# ContentProvider

하나의 안드로이드에 다수의 CP가 존재합니다.

그렇다면, 내 앱과 CP를 어떻게 통신 시켜야 할까요?

또 어떻게 동기화를 유지해야 할까요?

그게 바로 `val resolver = contentResolver` 입니다.