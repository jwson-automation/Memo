# 타이머

## 코드

```kotlin

class RunOnUiThreadActivity : AppCompatActivity() {

    lateinit var binding : ActivityRunOnUiThreadBinding

    private var isRunning = false
    private var time = 0

    private lateinit var timerTask: Timer

    private var index :Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRunOnUiThreadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.button.setOnClickListener {
            startActivity(Intent(this, AsyncTaskActivity::class.java))
        }

        binding.fabStart.setOnClickListener {
            isRunning = !isRunning
            if(isRunning) start()
            else pause()

        }

        binding.fabReset.setOnClickListener {
            reset()
        }

        binding.btnLab.setOnClickListener {
            if(time != 0) {
                lapTime()
            }
        }
    }

    // start 버튼을 누르면 호출되는 메서드
    private fun start() {
        // 시작 버튼을 일시정지 이미지로 교체
        binding.fabStart.setImageResource(R.drawable.ic_pause)

        // period 마다 동작하는 timer객체 생성. 10ms 마다 실행
        timerTask = kotlin.concurrent.timer(period = 10) {

            time++ // period = 10 : 0.01초마다 time 을 1씩 증가
            val sec = time / 100    // 나눗셈의 몫 : 초 부분
            val milli = time % 100    // 나눗셈의 나머지 : 밀리초 부분

            // UI 조작을 위한 메서드
            runOnUiThread {
                binding.secText.text = sec.toString()
                binding.milliText.text = milli.toString()
            }
        }
    }

    // pause 버튼을 누르면 호출되는 메서드로, Timer 를 일시정지
    private fun pause() {
        // 일시정지 버튼을 시작 이미지로 교체
        binding.fabStart.setImageResource(R.drawable.ic_play)
        timerTask.cancel()
    }

    // lapTime 버튼을 누르면 호출되는 메서드로, Timer 의 현재 시간을 기록
    private fun lapTime() {

        val lapTime = time // 메서드를 호출했을 때의 시간 저장

        val textView = TextView(this).apply {
            textSize = 20f
            text = "${lapTime / 100}.${lapTime % 100}"
        }

        // 생성한 TextView 를 레이아웃에 추가
        // index : 0 은 레이아웃 최상단에 추가함을 의미
        binding.lapLayout.addView(textView, 0)
        index++ // 추가된 TextView 의 개수를 기록
    }

    // reset 버튼을 누르면 호출되는 메서드로, Timer 와 기록 모두를 초기화함
    private fun reset() {
        timerTask.cancel()

        time = 0  // 시간저장 변수 초기화
        isRunning = false  // Timer 작동 유무를 나타내는 isRunning 변수 false 세팅
        binding.fabStart.setImageResource(R.drawable.ic_play)

        // Timer TextView 초기화
        binding.secText.text = "0"
        binding.milliText.text = "00"

        // Timer 기록을 위한 lapLayout 초기화
        binding.lapLayout.removeAllViews()
        index = 1
    }
}
```
