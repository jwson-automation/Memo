# ViewModel

: 안드로이드의 생명주기를 분리 시켜, Activity가 재실행되어도 데이터가 소멸되지 않도록 합니다.

: ViewModel 객체는 액티비티의 LifeCycle 상태가 종료(FINISH)될 때까지 소멸되지 않습니다.

예를 들어, Rotation 될 때, onDestroy와 onCreate가 호출되는데, ViewModel은 완전히 Activity가 Finished 될 때가지 유지해줌으로 데이터를 소중하게 보관해준다.
( onSavedInstance와 NewInstance를 사용하거나, Configuration Change를 못하도록 회전을 막아버리면 됩니다.)

## 실습

0. 의존성 주입

```gradle
 //viewmodel 추가
    implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.4.1'
```

1. 뷰모델 사용하지 않음 ( onSavedInstance를 사용하면 보관 가능)

```kotlin
//버튼 클릭시 카운트 증가
        binding.increaseButton.setOnClickListener {
            count++
            binding.countText.text = count.toString()
        }
```

2. 뷰모델 사용함

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

비정상적으로 종료될 때를 방지하기 위해서 onSaveInstanceState를 이용해서 State에 넣어주고 Create에 Bundle을 넣어준다.

이후에는 SaveStateHandle,등의 다른 방법도 사용가능하다.

## 프래그먼트 viewModel 초기화

provider > Fragment-ktx

프래그먼트를 사용한다고 하면, Fragment-ktx라는 라이브러리를 넣고,
viewModels를 달아주기만 하면 초기화 문제가 해결된다!

이 구조를 조금더 응용하자면, 모든 Fragment의 데이터를 하나의 뷰 모델에 담아서 공유하는 것이 가능하다!

그래서 프래그먼트는 2가지의 선택을 할 수 있다.

1. 나만의 뷰 모델 만들기
2. 액티비티의 뷰 모델에 접근하기

##

1. 뷰 모델 사용 전
   : 콜백 리스너를 이용해서 액티비티에서 데이터를 관리하고 있다.

```
class NewActivityWithFragment : AppCompatActivity(), CommunicationCallback {

    private var count = 0
    private lateinit var binding: ActivityNewWithFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewWithFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_container, BlankFragment1().apply{
                listener = this@NewActivityWithFragment
            }).commit()

        binding.fragment1.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, BlankFragment1().apply{
                    listener = this@NewActivityWithFragment
                }).commit()
        }

        binding.fragment2.setOnClickListener {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, BlankFragment2().apply{
                    listener = this@NewActivityWithFragment
                }).commit()
        }
    }

    override fun increaseCount() {
        count++
    }

    override fun decreaseCount() {
        count--
    }

    override fun getCount():Int {
        return count
    }
}

class BlankFragment2 : Fragment() {

    var listener : CommunicationCallback? = null

    private var _binding: FragmentBlank2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentBlank2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        printCount()

        binding.buttonPlus.setOnClickListener {
            listener?.increaseCount()
            printCount()
        }
        binding.buttonMinus.setOnClickListener {
            listener?.decreaseCount()
            printCount()
        }
    }

    private fun printCount(){
        binding.textResult.text = listener?.getCount().toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

```

2. 사용후

```kotlin
class BlankFragment2 : Fragment() {

    private val activityVeiwModel by activityViewModels<NewActivityVeiwModel>()

    private var _binding: FragmentBlank2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentBlank2Binding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        printCount()

        binding.buttonPlus.setOnClickListener {
            activityVeiwModel.increaseCount()
            printCount()
        }
        binding.buttonMinus.setOnClickListener {
            activityVeiwModel.decreaseCount()
            printCount()
        }
    }

    private fun printCount(){
        binding.textResult.text = activityVeiwModel.count.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```
