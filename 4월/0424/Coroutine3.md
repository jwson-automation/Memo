# CoroutineScope

launch = builder

- Coroutine은 Kotlin과 별개의 개념입니다.
- Python에서도 사용할 수 있습니다.

## 메소드

### `GlobalScope.launch{ }`

: 일반적으로 사용을 권장하지 않습니다.
: application 전체 life cycle의 전체 범위에 해당되는 스코프 하나를 시작!

### `CoroutineScope.launch{}`

- Coroutine은 딱히 Thread를 block 하지 않습니다. 코루틴이 실행중일 때 프로그램을 종료되면 그냥 그대로 끝입니다.

### `runBlocking{}`

: 현재 스레드를 못 끝내도록 멈춰두기 + 코루틴 생성을 해주는 친구입니다.
: 이것도 코루틴 안에서 사용을 권장하지 않습니다!
: 보통 테스트 할 때 자주 사용합니다!

- Coroutine 속 Coroutine을 만들때, 단순히 launch를 하면 부모의 속성을 따라가는 코루틴이 생성됩니다. 아래 예시

```koltin
// 그냥 다른 두개의 코루틴임
runBlocking{
GlobalScope.launch{}
}

// 같은 속성의 코루틴임
runBlocking{
launch{}
}
```

### `delay()`

- Coroutine은 `delay`로 지연시킬 수 있습니다.
- 일반 메소드에서는 사용할 수 없습니다. Coroutine에서만 사용 가능합니다.
  ( 일반 메소드에서 쓰면 오류 문구와 함께 suspend 해야 한다고 나옵니다.)

## coroutineScope

### 소문자 coroutineScope

(대문자도 있습니다.)

: doWorld()가 끝날때까지 대기시킵니다.
: " 내 밑으로 내가 다할 때까지 기다려! "

```kotlin
// Sequentially executes doWorld followed by "Done"
fun main() = runBlocking {
    //runBlocking이므로, 현재 block 끝날때까지 대기.
    //doWorld가 coroutineScope이므로, doWorld가 끝날때까지 대기
    doWorld()
    println("Done")
}

// Concurrently executes both sections
suspend fun doWorld() = coroutineScope { // this: CoroutineScope
    launch {
        delay(2000L)
        println("World 2")
    }
    launch {
        delay(1000L)
        println("World 1")
    }
    println("Hello")
}
```

### job & join

job & join을 사용하면 소문자 coroutine 처럼 대기시킬 수 있습니다.

```kotlin
fun main() = runBlocking {
    val job = launch {
        delay(300L)
        println("World!")
    }

    println("Hello,")
    job.join()
    println("Done.")
}
```

`Hello, Wrold! Done.

### repeat(){}

루프를 걸어줍니다!

아래 두 경우, 쓰레드와 코루틴을 비교하면

쓰레드가 훨씬 시간이 오래 걸립니다!!!!!!!!!!!!

```kotlin
fun main()  {
    runBlocking {
        repeat(100_000) { // launch a lot of coroutines
            launch {
                delay(5000L)
                print(".")
            }
        }

//        repeat(100_000) { // start a lot of Thread
//            thread {
//                Thread.sleep(1000L)
//                print(".")
//            }
//        }

    }
}
```

## runBlocking vs corouitineScope

둘 다 현재 블락 하고 다음걸로 넘어갑니다.

runBlocking은 못끝내게 막아! -> 일반함수 ( regular function )

coroutineScope는 다음걸 못하게 막아! ( suspend function )
