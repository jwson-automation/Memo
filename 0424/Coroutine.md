# Coroutine

코로우티네

## Thread란?

: 운영체제에 의해 메모리에 적재되어 실행중인 프로그램

### Thread 만들기

`stop`이라는 메소드는 사용하지 않는 걸 권장한다.

### 1번 방법 : 쓰레드 상속하는 클래스를 불러오기

```Kotlin
        isRunning = true
        val thread = ThreadClass()
        thread.start()

    inner class ThreadClass : Thread() {
        override fun run() {
            while(isRunning) {
                sleep(100)
                Log.d(TAG, System.currentTimeMillis().toString())
// binding.helloTextview.text = System.currentTimeMillis().toString()
            }
        }
    }

    override fun onRestart() {
        super.onRestart()

        isRunning = true
    }
    override fun onStop() {
        super.onStop()

        isRunning = false
    }
```

- 위 코드에서

`binding.helloTextview.text = System.currentTimeMillis().toString()`를 추가하면 오류가 발생합니다.

Only the original thread that created a view ...

이 이유는 메인쓰레드가 아닌 다른 쓰레드에서는 화면을 그릴 수 없기 때문입니다.

--> TextView의 가로 길이를 match_parent로 하면 이 오류가 사라지는데 그 이유는 `길이폭변화`가 있을 때 화면을 다시 그리기 때문, 단순 데이터 체인지는 메인이 아니어도 가능하다.

--> UI Thread == Main Thread

### 2번 방법 : 어나니머스 메소드 만들어서 쓰레드 바로 실행하기

```Kotlin
Thread{

}.start

```

### 3번 방법 : `function Thread`

```kotlin
thread{

}
```
