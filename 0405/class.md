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

`ACTION_VIEW`에서 `Uri.parse("geo:36.1138655,128.4038385")`로 인텐트를 암시적으로 보내면, 구글 지도가 연결된다.

`ACTION_VIEW`에서 `Uri.parse("content://contacts/people/1")`을 보내면 연락처가 연결된다!

`ACTION_INSERT`를 하면 연락처 추가도 가능!

완전 신기하지 않나??

`intent = Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:01022222222))`
`intent.putExtra("sms_body","SMS 연습입니다.)`

이렇게 하기만해도 특정 전화로, 문자 "SMS연습입니다."를 보내기 직전인 창을 띄워줍니다. 개굳!

`ACTION_DIAL` 다이얼 걸어줘!

## Permission

권한관련

```
보통 퍼미션 라이브러리를 가져와서 쉽게 사용하는 편이지만, 그렇게 하더라도 하나하나의 반례를 처리해주는 데 많은 양의 코드가 필요하다.

1. 권한이 거절당했을 때,
2. 특정 권한이 거절당했고, 다른 권한은 있을 때, 등 ...

Runtime Permissions > 코드로 구현해야하는 퍼미션.. 이게 어렵다.
```

### install permission

그냥 manifest에 넣으면 설치할때 요청하는 퍼미션입니다.

### Runtime Permission

실행 될 때 요청하는 퍼미션입니다.

- ManiFest에 넣기
- 권한 체크, 요청창 띄우고 응답받기, 최종권한 없을 때 설정으로 이동... 등을 구현해줘야합니다.

만약에 지도를 띄운다면 가장 최근의 위치정보를 어떻게 띄우죠?

### 지도 위치 저장 샘플 코드

```Kotlin
class MainActivity1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Location Manager 획득.
        val manager = getSystemService(LOCATION_SERVICE) as LocationManager

        // 가장 최근의 위치정보
        var location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)

        var lat = location?.latitude  //위도
        var lon = location?.longitude //경도

        Log.d(TAG, "onCreate: 위도 : $lat , 경도 :$lon")

        //1. 권한 체크
        var coarseResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        var fineResult = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        Log.d(TAG, "onCreate.coarseResult: $coarseResult")
        Log.d(TAG, "onCreate.fineResult: $fineResult")
    }
}
```

## Task

## Activity Mode

## Activity Change

### 그외

- 네이버가 무거운 이유
  구글에서 웹뷰 문제가 발생한 적이 있어서, 웹뷰 베이스 버전을 그냥 앱에 넣어버렸다. 구글걸 가져오는 방향으로 실행하면 문제가 발생할 수 있기 때문!

- 모든 Manager들은 GetSystemService(LOCATION_SERVICE)로부터 가져온다.
