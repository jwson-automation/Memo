# Activity & Intent

오늘 Task 부분이 어렵습니다.

유저 경험과 연관되는 부분이라서, 정확한 정답은 없습니다.

## 목차

- [Activity](#activity)
- [Activity LifeCycle](#activity-lifecycle)
- [Activity Status](#Activity-Status)
- [Intent](#Intent)
- [Explict(명시적) Intent](#Explict-Intent)
- [Implict(암시적,묵시적) Intent](#implict-intent)

- [Task](#task)
- [Activity 실행 모드](#activity-mode)
- [Task 내 Activity 정리 및 변경](#activity-change)

## Activity

- UI를 제공하기 위한 가장 기본이 되는 앱 컴포넌트
- Manifest파일에 선언이 되어야함
- Activity가 아닌 `AppCompatActivity`를 상속받아서 사용할 예정

## Activity LifeCycle

onCreate >
onStart ( 포커스가 없는 상태 ) >
onResume ( 포커스를 받은 상태 ) >
Activity 실행 >
onPause ( 다른 액티비티가 보임 ) >
onStop >
onDestroy >
종료

```
Activity도 theme를 다이얼로그로 바꿔주면 다이얼로그처럼 나옵니다.

이런 상태라면 뒤 Activity와 앞 Acivity 두 개로 나누어 지는데, Focus가

어디에 있냐에 따라서 Resume이냐 Pause냐가 결정됩니다.
```

- onStop에 `finish()`를 넣으면 화면을 내렸다가 올릴때마다 재시작 되도록 할 수 있다. [개발자 모드에서 항상 죽이도록 설정 하는 것이 가능한다]

- 상황에 따라 메모리가 부족하면 안드로이드 시스템에 의해 Stop이 Destroy로 이어질 수도 있다.

- Activity 1 > Activty 2로 간 뒤에 뒤로가기를 누르면 Activity2는 Destroy된다.

- 화면 회전시에 Destroy가 된 뒤 다시 Create된다! [ 그래서 실제로 화면 회전을 막아둔 앱도 많다.]
  : 화면 회전 시에는 Device Rotation -> onSaveInstanceState - onDestroy - onCreate -> on RestoreInstanceState로 이어진다.

## Activity Status

화면 회전 전에 이렇게 저장해주면

```Kotlin
override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("tmp",tmp)
    }
```

아래와 같이 onCreate에서 받아와줄 수 있다.

```
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState != null){
            tmp = savedInstanceState.getInt("tmp")
        }
```

## Intent

지금까지 썼던 Intent는 명시적 Intent 였습니다.

## Explict Intent

명시적이기만 하다면, 다른 패키지의 Activity까지도 갈 수 있다!
`Intent(this, 정우Activity::java.class)`

## Implict Intent

액션과 데이터를 지정했지만, 호출할 대상에 따라 달라질 수 있는 경우에 사용
`Intent(Intent.ACTION_CALL,uri)`
`액션은 정했고, 데이터는 지정했다.`
하지만 누가?! choose 어떤걸로 갈건데? -> 전화라면 전화 앱 브라우저라면 브라우저 앱...

## Task

## Activity Mode

## Activity Change
