# 실습 3

LiveData 실습 2 + ViewModel을 추가

## 코드 읽기

1. viewModel 가져오기

```kotlin
private val viewModel : MainViewModel by viewModels()
or
private val viewModel by viewModels<MainViewModel>()
```

2. viewModel 생성하기
   데이터 만들기 + get 메소드 만들기

```kotlin
class MainViewModel : ViewModel() {
    private val _elapsedTime = TimerLiveData()
    fun getElapsedTime() = _elapsedTime
}
```

3. viewModel을 이용해서 데이타 업데이트 해주기

```kotlin
viewModel.getElapsedTime().observe(this) { time ->
            binding.timerTextview.text = time.toString()
        }
```

4. 위 방법을 추가함으로써 LiveData가 사라지지 않고 유지됩니다.

## 코드

0. Dependency 추가

1. LiveData

```kotlin
class TimerLiveData : LiveData<Long>() {

    private val initialTime: Long = SystemClock.elapsedRealtime()
    private var timer: Timer? = null

    override fun onActive() {
        timer = Timer()
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
                postValue(newValue)
            }
        }, ONE_SECOND, ONE_SECOND)
    }

    override fun onInactive() {
        timer?.cancel()
    }

    companion object {
        private const val ONE_SECOND = 1000.toLong()
    }
}
```

2. ViewModel

```kotlin
class MainViewModel : ViewModel() {

    private val _elapsedTime = TimerLiveData()

    fun getElapsedTime() = _elapsedTime

}
```

3. MainActivity

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel : MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getElapsedTime().observe(this) { time ->
            binding.timerTextview.text = time.toString()
        }

    }
}
```
