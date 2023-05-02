# 실습 4

Fragment 추가

## 코드 읽기

한 액티비티에 2개의 프래그먼트가 있다고 했을 때,

두 프래그먼트가 라이브데이터를 observe하고 있다면

하나만 바꿔도 둘 다 쉽게 적용되겠군요?

1. ViewModel의 데이터를 라이브 데이터로 변경한다.

2. Observer를 달아준다.

```kotlin
    fun printCount(){
//        binding.textResult.text = activityWithFragmentViewModel.count.toString()
        activityWithFragmentViewModel.count.observe(viewLifecycleOwner){
            binding.textResult.text = it.toString()
        }
    }
```

3. 두 Fragment가 ViewModel의 LiveData와 Observer, 바인딩 된다.

## 코드

1. viewModel

변경 전

```kotlin
class NewActivityViewModel:ViewModel() {
    //사용자의 클릭 수를 세는 변수
    var count = 0
        private set

    //사용자가 클릭 했을 때 클릭수 를 증가시키는 메소드
    fun increaseCount(){
        count++
    }

    fun decreaseCount(){
        count--
    }
```

변경 후

```kotlin
class NewActivityViewModel : ViewModel() {
    //사용자의 클릭 수를 세는 변수

    private val _count = MutableLiveData<Int>().apply {
        value = 0 // ui
    }

    val count: LiveData<Int>
        get() = _count

    //사용자가 클릭 했을 때 클릭수 를 증가시키는 메소드
    fun increaseCount() {
        _count.value = (count.value ?: 0) + 1
    }

    fun decreaseCount() {
        _count.value = (count.value ?: 0) - 1
    }
}
```

2. mainActivity

```kotlin
private  val activityWithFragmentViewModel: NewActivityViewModel by viewModels()

    private lateinit var binding: ActivityMainWithFragmentBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainWithFragmentBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
```

3. Fragment 1

```kotlin
class BlankFragment1 : Fragment() {

    private  val activityWithFragmentViewModel: NewActivityViewModel by activityViewModels()

    private var _binding: FragmentBlank1Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBlank1Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        printCount()

        binding.buttonPlus.setOnClickListener {
            activityWithFragmentViewModel.increaseCount()
            printCount()
        }
        binding.buttonMinus.setOnClickListener {
            activityWithFragmentViewModel.decreaseCount()
            printCount()
        }
    }

    fun printCount(){
        binding.textResult.text = activityWithFragmentViewModel.count.toString()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
```

4. Fragment 2

```kotlin
class BlankFragment2 : Fragment() {

    private  val activityWithFragmentViewModel: NewActivityViewModel by activityViewModels()

    private var _binding: FragmentBlank2Binding? = null
    private val binding get() = _binding!!
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View {
        _binding = FragmentBlank2Binding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        printCount()

        binding.buttonPlus.setOnClickListener {
            activityWithFragmentViewModel.increaseCount()
            printCount()
        }
        binding.buttonMinus.setOnClickListener {
            activityWithFragmentViewModel.decreaseCount()
            printCount()
        }
    }

    fun printCount(){
        binding.textResult.text = activityWithFragmentViewModel.count.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
```

5. Fragment 변경 후

```kotlin
    fun printCount(){
//        binding.textResult.text = activityWithFragmentViewModel.count.toString()
        activityWithFragmentViewModel.count.observe(viewLifecycleOwner){
            binding.textResult.text = it.toString()
        }
    }
```

```kotlin
override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        printCount()

        binding.buttonPlus.setOnClickListener {
            activityWithFragmentViewModel.increaseCount()
//            printCount()
        }
        binding.buttonMinus.setOnClickListener {
            activityWithFragmentViewModel.decreaseCount()
//            printCount()
        }
    }
```
