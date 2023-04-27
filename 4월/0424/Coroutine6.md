# Coroutine

## 라이브러리 설정

```gradlew
//coroutine
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2'
```

## 액티비티를 코루틴으로 만들어보겠다!

```kotlin
class CoroutineScopeActivity : AppCompatActivity(), CoroutineScope {

    private lateinit var mJob: Job
    override val coroutineContext: CoroutineContext
        get() = mJob + Dispatchers.Main


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)
        mJob = Job()
        val textview = findViewById<TextView>(R.id.textView)

        mJob = launch {
            // 1. 데이터 입출력을 해야 하므로 IO Dispatcher에 배분
            val deferredInt = async(Dispatchers.IO) {
                arrayOf(3, 1, 2, 4, 5)
            }

            // 2. Sorting. CPU작업으로 Default Dispatcher에 배분
            val sortedDeferred = async(Dispatchers.Default) {
                val value = deferredInt.await()
                value.sortedBy { it }
            }

            // 3. TextView에 세팅하는 것은 UI 작업이므로 Main Dispatcher에 배분
            val textViewSettingJob = launch {
                val sortedArray = sortedDeferred.await()
                textview.text = sortedArray.toString()
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        mJob.cancel()
    }
}
```
