# 사진이 바뀌는 Thread

아래 처럼 사진이 자꾸 바뀌는 걸 만들기 위해서는

`Thread{}` 에서 바로 호출해서는 안됩니다.

Handler에 담아서 UI Thread를 통해서 수정을 시켜줘야 한다는 것이다.

`val handler = Handler(Looper.getMainLooper())` 이렇게!

핸들러 사용 안함 = 내가 바꿀래! 안돼 넌 권한이 없어!
핸들러 사용 함 = 바꿔줘! 그래 난 메인 쓰레드니까 해줄게!

## 코드

```Kotlin
class HandlerActivity : AppCompatActivity() {

    private val dogImageList = ArrayList<Int>()

    lateinit var binding : ActivityHandlerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHandlerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startActivity(Intent(this, TimerActivity::class.java))
        }

        for(i in 1..30) {
            dogImageList.add(R.drawable.dog1)
            dogImageList.add(R.drawable.dog2)
            dogImageList.add(R.drawable.dog3)
        }

        val handler = Handler(Looper.getMainLooper())
        Thread {
            for(i in dogImageList) {
                Thread.sleep(1000)
                handler.post {
                    binding.dogIv.setImageResource(i)
                }
            }
        }.start()
    }
}
```
