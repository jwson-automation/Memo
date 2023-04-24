# coroutine scope functions

suspend function 으로 정의되어 있는 추가적인 function 들이 있습니다.

withContext, withTimeout, coroutineScope, ...

## withContext

async, await 랑 비슷하다.

하지만 withContext는 결과를 바로 가져올 수 있다.

그냥 쉽게 다되면 가져와! 하는거임

```kotlin
fun main() = runBlocking{
    println("Start : before withContext")
    val time = withContext(Dispatchers.Default) {
        println("Start withContext")
        delay(3000)
        "after 3 seconds"
    }

    println("Start : after withContext")
    println("Result : $time")
}
```

finally랑 같이 써서 야 지금 없어! 좀 더 기다려! 를 표시할 수 있습니다.

```kotlin
fun main() = runBlocking {
    val job = launch {
        try {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        } finally {
            withContext(NonCancellable) {
                delay(1000)
                println("main : I'm running finally!")
            }
        }
    }

    delay(1300L)
    println("main : I'm tired of waiting!")
    job.cancelAndJoin()
    println("main : Now I can quit.")
}
```

### withTimeout

정해진 시간만큼 지나면 TimeOutException을 걸어주는 친구입니다.

withContext는 끝까지 기다림, TimeOut은 3분만 기다린다!! 끝내라!! 하는 것!

### coroutineScope (소문자임)

젤 중요한건 그냥! 내 밑으로 멈추게 하는 친구라는 거임

### CoroutineName

이름을 줘서 A라는 속성이 어디까지 상속되는지 볼 수 있습니다.

```kotlin
fun main() = runBlocking(CoroutineName("A")) {
    println("Before")
    longTask()
    println("After")
}

suspend fun longTask() = coroutineScope {
    launch {
        delay(1000)
        val name = coroutineContext[CoroutineName]?.name
        println("[$name] Finished task 1")
    }

    launch {
        delay(2000)
        val name = coroutineContext[CoroutineName]?.name
        println("[$name] Finished task 2")
    }
}

```
