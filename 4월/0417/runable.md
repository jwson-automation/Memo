# Runable interface vs Korutine

Runnable 인터페이스를 익명 클래스로 구현하는 것은 코드를 더욱 간결하게 작성하고, 다른 스레드에서 실행될 코드를 객체 형태로 생성하여 보다 유연하게 실행할 수 있도록 도와주는 방법입니다. 하지만, 이 방법은 꼭 필요한 상황이 아니라면 사용하지 않아도 됩니다.

실제로, Runnable 인터페이스를 구현하는 데에는 다양한 방법이 있습니다. 익명 클래스를 사용하는 것 외에도, 람다 표현식이나 함수 참조를 사용하여 Runnable 인터페이스를 구현할 수도 있습니다. 따라서, 개발자는 코드의 목적과 상황에 맞게 적절한 방법을 선택하여 사용하면 됩니다.

또한, Kotlin에서는 코루틴을 사용하여 다른 스레드에서 비동기적으로 실행할 수 있는 코드를 작성하는 것이 더욱 간결하고 직관적입니다. 따라서, 코루틴을 사용하는 것이 Runnable 인터페이스를 익명 클래스로 구현하는 것보다 더욱 추천됩니다.

## 예제

Runnable 인터페이스와 코루틴은 모두 다른 스레드에서 비동기적으로 실행될 수 있는 코드를 작성하는 방법입니다. 하지만, 두 방법은 몇 가지 차이점이 있습니다.

Runnable 인터페이스를 익명 클래스로 구현한 예제는 다음과 같습니다.

```Kotlin
// Runnable 인터페이스를 구현한 익명 클래스를 생성하는 예제
val runnable = object : Runnable {
    override fun run() {
        // 실행할 코드 작성
    }
}
```

위 코드에서는 Runnable 인터페이스를 구현한 익명 클래스를 생성하고, 이를 Thread 생성자에 전달하여 다른 스레드에서 실행합니다.

반면에, 코루틴을 사용하여 비동기적으로 실행할 코드를 작성하는 방법은 다음과 같습니다.

```Kotlin
val thread = Thread(runnable)
thread.start()

// 코루틴을 사용하여 비동기적으로 실행할 코드를 작성하는 예제
GlobalScope.launch {
    // 실행할 코드 작성
}

```

## 프로그레스바 + 코루틴

```Kotlin

class MainActivity : AppCompatActivity() {
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressBar = findViewById(R.id.progress_bar)

        // 버튼 클릭 시 코루틴 실행
        findViewById<Button>(R.id.button).setOnClickListener {
            GlobalScope.launch {
                for (i in 0..100) {
                    // 프로그레스바 업데이트를 UI 스레드에 전달
                    runOnUiThread {
                        progressBar.progress = i
                    }
                    delay(50) // 50 밀리초 대기
                }
            }
        }
    }
}

```

## 코루틴 vs Thread

프로그레스바 업데이트는 UI 스레드에서 이루어져야 합니다. 기존의 Thread를 사용하여 프로그레스바를 업데이트하는 경우, Thread가 UI 스레드와 분리되어 있으므로 UI 스레드에 직접적으로 접근할 수 없습니다. 따라서 Thread에서 UI 스레드로 접근할 수 있는 방법이 필요합니다.

코루틴은 비동기 처리를 위한 가벼운 스레드로, UI 스레드와 같은 스레드에서 실행됩니다. 따라서 코루틴에서는 UI 스레드에 직접 접근하여 프로그레스바를 업데이트할 수 있습니다. 또한, 코루틴은 스레드보다 가벼우므로 여러 개의 코루틴을 실행하는 것이 스레드를 사용하는 것보다 효율적입니다.

따라서, 코루틴에서도 프로그레스바를 업데이트할 수 있습니다. 하지만, UI 스레드에 직접적으로 접근하므로 안드로이드에서는 메인 스레드에서만 코루틴을 사용하는 것이 좋습니다. 또한, 코루틴에서도 오랜 시간이 걸리는 작업은 백그라운드 스레드에서 처리해야 합니다.
