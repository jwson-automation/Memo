# Couroutine Scope

대문자임!

GlobalScope = 애플리케이션과 라이프사이클이 동일함
CoroutineScope = 생성/종료에 맞게 만들고 소멸시킬 수 있는 코루틴

`쓰레드가 몇개인지 코루틴을 쓰기 시작하면 이제 내 알빠 아니다. 그래서 코루틴 그룹에서 돌아간다고 표현합니다.`

### Coroutine Context

- 코루틴을 쓰는 장소, 즉 쓰레드를 이야기한다.

### Builder

- 생성하는 방법을 말합니다.
- launch(), async(), runBlocking()이 있습니다.
- launch()는 쓰고 바이바이입니다.
  : join, cancel, start가 가능합니다. 근데 셋 다 필요한가..?
  : 대기, 취소, 혹시 시작안했다면 시작!

```kotlin
fun main() = runBlocking {
    var job = CoroutineScope(Dispatchers.Default).launch {
        delay(1000)
        println("test")
    }

    println("Start")
    job.join()
    println("Done")
}
```

- async()는 결과를 받을거라서 `Deffered<T>`를 리턴합니다! `await`로 기다리다가 받습니다.

```kotlin
fun main() = runBlocking {
    doSomething()
    println("Done")
}

private suspend fun doSomething() = coroutineScope {

    val one = async {
        delay(1000L)
        "result ONE"
    }
    val two = async {
        delay(2000L)
        "result TWO"
    }

    launch {
        println("test")
        val result = "one : ${one.await()} , two : ${ two.await()}"
        println(result)
    }
}
```

### Dispatcher

- 어떤 쓰레드를 사용해서 동작할지를 말합니다.
- Dispatchers.Main : 메인쓰레드, UI 쓰레드에서 동작시킵니다.
- Dispatchers.IO : Network, Disk IO 등을 작업하는 쓰레드에서 동작
- Dispatchers.Default : Main이외의 worker Thread에서 작업, CPU 사용이 많을때 사용합니다.

### CoroutineScope

- 가장 기본입니다. 소문자 코루틴은 블라킹을 하고, 대문자는 블라킹 하지 않습니다.

```kotlin
val scope = CoroutineScope(Dispatchers.Main)
scope.launch{

}
scope.launch(Dispatcher.IO){

}
```

### 즉시 실행, 지연실행 ( Lazy )

이 때 아까 있었던 runBlocking의 start() 메소드를 사용하게 된다.

lazy로 기다리고 있는 놈한테 일해! 하는것.

```kotlin
fun main(): Unit = runBlocking {
    val job = CoroutineScope(Dispatchers.Default).launch(start = CoroutineStart.LAZY) {
        println("CoroutineScope.launch Lazy Start")
    }

    val deferred = async(start = CoroutineStart.LAZY) {
        println("async Lazy Start")
        "async Lazy Result"
    }

    println("Start")

//    job.start()		// launch는 start() 또는 join()으로 해당 코루틴을 실행
     job.join()	// start()와 동일하게 실행시킴. join은 결과 대기까지

    val result = deferred.start()	// async는 start() 또는 await()으로 해당 코루틴을 실행
                                    // start()는 결과를 대기하지 않으므로 실행결과인 true를 리턴
//   val result =  deferred.await()	// await는 실행시키고 대기까지

    println("result : $result")
}
```

### GlobalScope

CoroutineScope을 상속받아서 구현된 친구로, 딱히 필요없슴다!

### 기타

`ANR`

- 앱에서 응답하지 않는 경우 응답을 기다릴지 아니면 앱을 종료할지 묻는 대화상자가 표시됩니다. 이러한 대화상자가 표시되는 상황을 '애플리케이션 응답 없음'(ANR) 오류라고 합니다. Android 10 이하 기기의 ANR은 Play Console에서만 확인할 수 있습니다.
