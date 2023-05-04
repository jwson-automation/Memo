# 프로젝트 템플릿

## 필요성

- 항상 Activity와 Fragment에 거의 모든 코드를 작성하는 방법은 코드의 가독성을 떨어뜨림

- 유지보수도 어려움

- 역할에 따라 클래스를 분리하여 가독성과 재사용성을 높여야함

- 프로젝트 템플릿을 사용하면 일관성 있게 구성가능

## 코드 보기

추가된 것

1. CookieInterceptor + XAccessTokenIntercepter + AddCookiesInterceptor

: 기존에는 신경쓰지 않았던 쿠기의 관리를 모두 통신 시에 담아줄 수 있도록 담아준다.

## 코드 읽기

1. Application Class

앱 시작과 동시에

`SharedPreferences`를 사용할 수 있게 선언합니다.
`Retrofit`을 생성합니다.

```
companion object {
        // 만들어져있는 SharedPreferences 를 사용해야합니다. 재생성하지 않도록 유념해주세요
        lateinit var sharedPreferences: SharedPreferencesUtil

        // JWT Token Header 키 값
        const val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"
        const val SHARED_PREFERENCES_NAME = "SSAFY_TEMPLATE_APP"
        const val COOKIES_KEY_NAME = "cookies"

        // Retrofit 인스턴스, 앱 실행시 한번만 생성하여 사용합니다.
        lateinit var retrofit: Retrofit
    }
```

2. Base Activity 생성

먼저 `AppCompatActivity()` 를 상속 받아서 Base Activity라는 친구를 만들어 줍니다.

```
abstract class BaseActivity<B : ViewBinding>(private val inflate: (LayoutInflater) -> B) : AppCompatActivity()
```

`메소드 레퍼런스`로 아래 처럼 넣어줍니다.

```
class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
```

해석하자면

```
ActivityMainBinding = > Base Activity로 넘어가서 => inflate 되고 => setContentView() 됩니다.
```

- 추가 설명 _람다식, 메소드 레퍼런스_

위 식이 조금 어려워서 추가 설명을 달았습니다.

```kotlin
// 평범한 메소드
fun add(x:Int, y:Int):Int{
    return x+y
}

// lambda를 이용한 메소드
fun calculator(x:Int,y:Int, calculation:(Int,Int) -> Int) : Int {
    return (calculation(x,y))
}

fun main(){
    println(add(1,2)) // 일반 함수 호출
    println(calculator(3, 2) { x, y -> x * y }) // 람다 함수 호출

    println(plus(2,5,::add)) // 메소드 레퍼런스
}
```

3. Base Fragment 생성

- 쉽게 Fragment에서 bind와 R.id를 가져와주고

- 그 이후에 onCreateView를 실행시켜 연결을 해줍니다.

- 그 후 필요한 함수를 프래그먼트에서 호출해서 사용해주면 됩니다.

```kotlin
abstract class BaseFragment<B : ViewBinding>(
    private val bind: (View) -> B,
    @LayoutRes layoutResId: Int
) : Fragment(layoutResId) {
    private var _binding: B? = null
    lateinit var mLoadingDialog: LoadingDialog

    protected val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bind(super.onCreateView(inflater, container, savedInstanceState)!!)
        return binding.root
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    fun showCustomToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }
}
```

```kotlin
private const val TAG = "HomeFragment_싸피"
class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind, R.layout.fragment_home) {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        registerObserver()

        binding.homeButtonTryGetJwt.setOnClickListener {
            showLoadingDialog(requireContext())
            homeViewModel.getUserData()

        }

        binding.homeBtnTryPostHttpMethod.setOnClickListener {
            val userId = binding.homeEtId.text.toString()
            val password = binding.homeEtPw.text.toString()
            val postRequest = PostSignUpRequest(userId = userId, password = password)

            showLoadingDialog(requireContext())
            homeViewModel.getSignUpUser(postRequest)
        }
    }

    private fun registerObserver(){
        homeViewModel.user.observe(viewLifecycleOwner){
            if(it != null){
                binding.homeButtonTryGetJwt.text = it.message
                Log.d(TAG, it.toString())
                showCustomToast("${it.result}")
            }else{
                showCustomToast("오류...")
            }
            dismissLoadingDialog()
        }

        homeViewModel.signUpUser.observe(viewLifecycleOwner){
            if(it != null){
                binding.homeBtnTryPostHttpMethod.text = it.message
                Log.d(TAG, it.toString())
                showCustomToast("${it.result}")
            }else{
                showCustomToast("오류...")
            }
            dismissLoadingDialog()
        }

    }
}
```
