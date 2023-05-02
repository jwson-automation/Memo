# 라이브데이터 + 뷰모델 + HTTP

0-1. 퍼미션 추가
<uses-permission permission.INTERNET>

0-2. 의존성 추가

1. Retrofit을 이용해서 HTTP 요청을 해줍니다.

2. Retrofit 함수로 LiveData를 업데이트 해줍니다.

```kotlin
class ActivityViewModel : ViewModel() {
    private val _postList = MutableLiveData<List<Post>>()
    val postList: LiveData<List<Post>>
        get() = _postList

    fun getPostList() {
        val postInterface = ApplicationClass.wRetrofit.create(PostService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            _postList.value = postInterface.getPosts()
        }
    }
}
```

3. CoroutineScope -> vieModelScope
   viewModel이 붙어있는 thread에서 Coroutine Scope 형성

```kotlin
viewModelScope.launch {
            _postList.value = postInterface.getPosts()
        }
```

소스코드

```kotlin
public val ViewModel.viewModelScope: CoroutineScope
    get() {
        val scope: CoroutineScope? = this.getTag(JOB_KEY)
        if (scope != null) {
            return scope
        }
        return setTagIfAbsent(
            JOB_KEY,
            CloseableCoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)
        )
    }
```

보면 알겠지만 Dispatcher.Main으로 자동 연결됩니다.

## 기타

test용 json 만들어주는 url : https://jsonplaceholder.typicode.com/
