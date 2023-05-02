# 뷰 모델이 강제 종료 될 때

프로세스 킬 방법 : 개발자도구 > `don't keep activities`

savedInstance를 직접 하는것이 아닌 자동으로 하게 해주는 `SavedStateViewModel`이 있습니다!

viewModelProvider에 기본적으로 내장되어 있습니다 :)

## 코드 읽기

아래 코드에서는 직접 bundle에 내가 데이터를 넣어주고 있습니다.

그래서 고쳤습니다!

`handle:SavedStateHandle`만 추가해주면 내가 안넣어줘도 됩니다!
_하지만, 너무 무거운 데이터를 넣는것은 권장하지 않습니다._

_돌발 상황에 무거운 데이터를 모두 넣기 힘들기 때문!_

수정 전

```koltin
class MainViewModel(initCount:Int):ViewModel() {
    //사용자의 클릭 수를 세는 변수
    var count = initCount
        private set

    //사용자가 클릭 했을 때 클릭수 를 증가시키는 메소드
    fun increaseCount(){
        count++
    }
}
```

수정 후

```kotlin
class MainViewModel(private val handle:SavedStateHandle):ViewModel() {
    //사용자의 클릭 수를 세는 변수
    var count = handle.get<Int>("count") ?: 0
        set(value){
            handle.set("count", value)
            field = value
        }

    //사용자가 클릭 했을 때 클릭수 를 증가시키는 메소드
    fun increaseCount(){
        count++
    }
}
```

```kotlin
viewModel =  ViewModelProvider(this,
            SavedStateViewModelFactory(application, this)
        )[MainViewModel::class.java]
```

## 코드

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this,
            MainViewModelFactory(savedInstanceState?.getInt(KEY_COUNT) ?: 0)
        )[MainViewModel::class.java]

        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countText.text = viewModel.count.toString()
        //버튼 클릭시 카운트 증가
        binding.increaseButton.setOnClickListener {
            viewModel.increaseCount()
            binding.countText.text = viewModel.count.toString()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_COUNT, viewModel.count)
    }

    companion object{
        private const val KEY_COUNT="count"
    }
}
```

```koltin
class MainViewModel(initCount:Int):ViewModel() {
    //사용자의 클릭 수를 세는 변수
    var count = initCount
        private set

    //사용자가 클릭 했을 때 클릭수 를 증가시키는 메소드
    fun increaseCount(){
        count++
    }
}
```

```kotlin
class MainViewModelFactory(private val initCount:Int) :ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(initCount) as T
    }
}
```

## 수정된 코드

```kotlin
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel =  ViewModelProvider(this,
            SavedStateViewModelFactory(application, this)
        )[MainViewModel::class.java]


        binding.countText.text = viewModel.count.toString()
        //버튼 클릭시 카운트 증가
        binding.increaseButton.setOnClickListener {
            viewModel.increaseCount()
            binding.countText.text = viewModel.count.toString()
        }

    }
}
```

```kotlin
class MainViewModel(private val handle:SavedStateHandle):ViewModel() {
    //사용자의 클릭 수를 세는 변수
    var count = handle.get<Int>("count") ?: 0
        set(value){
            handle.set("count", value)
            field = value
        }

    //사용자가 클릭 했을 때 클릭수 를 증가시키는 메소드
    fun increaseCount(){
        count++
    }
}
```
