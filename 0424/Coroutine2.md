# Coroutine

- Blocking vs Non-Blocking
  1인 기업
  다인 기업

- Sync vs Async
  내가 다됐는지 물어보기
  다하면 나한테 알려주기

- 루틴 (메인루틴, 서브루틴)
  루틴 : 일
  메인루틴 : 전체의 동작 절차를 표시하도록 만드는 핵심 역할
  서브루틴 : 반복되는 작업들을 붙여둔것, 하위 루틴!

- 코루틴
  루틴의 메인과 서버 개념이 없어 마구마구 호출 가능!

## 요약

일반적인 프로그램이 특정 메소드에 갔다가 리턴해서 돌아오는 거였다면,
( 절차적으로 순서대로 진행하는 거였다.)

코루틴은 그런거 없다! 진입점도 없고, 그런 절차도 신경쓰지 않는다.
( 시작과 끝이 있지만, 실행을 처음부터 끝까지 할 필요 없다.)
( 그냥 중간에 들락 날락 할 수 있는 메소드들이 대기중이라고 보면 된다.)

일반 : 테트리스처럼 플로우대로 동작이 흘러간다.
코루틴 : 마인크래프트처럼 내가 뭘 할거면 도구를 꺼내서 하면 됨

쓰레드 : CPU에서 자리를 뺄려면, 쓰레드를 `슬립`(제어권박탈)시켜야 했다.
코루틴 : 굳이 제어권 박탈 시킬 필요 있음? 걍 그대로 왔다갔다 쓰겠음!

## 코드

`fun Int.countDown(){}` -> int값을 확장

`for index this downTo 1` -> 현재값에서 1까지 낮추면서 반복

`delay` -> 슬립이 아닌 딜레이를 준다.

`CoroutineScope.launch`이 뭔지는 모르겠지만

이 코루틴 버튼을 연속적으로 누르면!!

예전에 쓰레드를 한꺼번에 실행시켰을때는 CPU 순서가 엇갈려 이상한 결과가 나오는 문제가 발생했었다!

하지만, 코루틴의 경우에는 슬립과 running상태가 변경되는게 아닌, 그냥 다 running상태이기 때문에 자연스럽게 결과가 곂쳐져서 제공된다.

```kotlin
class MainActivity : AppCompatActivity(){

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fab.setOnClickListener{
            CoroutineScope(Dispatchers.Main).launch {
                10.countDown(++currentIndex)
            }
        }

        binding.button.setOnClickListener {
            startActivity(Intent(this, CoroutineScopeActivity::class.java))
        }
    }

    var currentIndex = 0

    private suspend fun Int.countDown(currentIndex: Int) {
        for (index in this downTo 1) {
            binding.textView.text = "Now index $currentIndex Countdown $index"
            delay (1000)
        }
        Log.i (TAG, "Now index $currentIndex Done!")
    }

}
```
