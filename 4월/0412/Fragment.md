# Fragment

: Activity 위에 올라가는 화면의 일부, 부분화면만 바꾸고 싶습니다. (특히 바텀네브바에서)

1. Activity처럼 Fragment도 별개의 생명주기가 있습니다.

먼저, Activity에서는

비정상적으로 종료될 수 있기 때문에, `onSaveInstanceState(Bundle)`를 사용 가능합니다.

`onRestartInstanceState(Bundle)`에서 `onCreate(Bundle)`의 번들을 수정해줍니다.

2. Fragment는 화면이 바뀔 때 데이터 전송은 어떻게 하면 될까요?

이건 내일 할겁니다!

## Fragment란?

일단, Acitivty만으로 화면을 구성해도 됩니다!

초기에 안드로이드가 개발되고, Tablet을 만들려고 하다보니 큰 화면에서 액티비티의 단점이 발견되었습니다.

바로, 화면의 일부분만 변경되었는데도 변경사항 적용을 위해서는 액티비티 전체를 다시 불러와야 한다는 점입니다.

그래서 `Fragment`라는 Activity안에서 갱신이 가능한 친구를 만들었습니다!

따라서 아래와 같은 특징을 가집니다.

- Fragment 생명주기는 Activity의 생명주기에 영향을 받습니다. ( 함께 Pause 되고, 함께 Destroy 됩니다.)

- 또, Activity 안에서 Fragment만의 LifeCycle을 가지고 있습니다. ( 갱신을 위해서 ) ( `FragmentManager` )

- Android Jetpack 라이브러리 중 ViewPager2, BottomNavigationView, 등은 Fragment와 호환되도록 설계되어 있습니다. ( 써야 합니다. )

## 실습

1. `FrameLayout`을 만들어 줍니다.

2. `Fragment`를 만들어 줍니다.
   ` 왜 companion으로 newInstance를 만들어 줬을까?를 고민해봐야 합니다.`

3. `Acitivty`에서 `AFragement()` 해주면 됩니다.

: 하지만! `AFragment.newInstance(params)`를 사용하는 방법을 권장합니다!
왜냐하면, `Fragment`의 특정값(params)을 `Bundle`로 만들어서 유지시키기 위함입니다. 비정상적인 종료에 대응이 가능합니다.

4. `supportFragmentManager`를 이용해서 넣어줍니다.

```kotln
private fun initBtn() {
        binding.fragABtn.setOnClickListener() {
            supportFragmentManager.beginTransaction().replace(
                binding.fragmentContainer.id,
                AFragment(),
            ).commit()
        }
        binding.fragBBtn.setOnClickListener() {
            supportFragmentManager.beginTransaction().replace(
                binding.fragmentContainer.id,
                BFragment(),
            ).commit()

        }
        binding.removeBtn.setOnClickListener() {
            val current = supportFragmentManager.findFragmentById(binding.fragmentContainer.id)
            if (current != null) {
                supportFragmentManager.beginTransaction()
                    .remove(current).commit()
            }
        }
    }
```

## LifeCycle

: Activity에서는 onCreate가 하는 일의 범위가 통합적인데, Fragment에서는 모두 분리되어 있습니다. `Fragment`가 이후에 개발된 것이라서 그렇다고 생각해도 좋을 것 같고, Activity와의 연관성을 생각해서 쪼갠거라고 생각해도 좋을 것 같습니다.

`onAttach()` : 가장 먼저 붙을 때
`onCreate()` : 값 세팅
`onCreateView()` : Layout inflate 단계
`onViewCreated()` : View 생성 완료, UI 초기화 작업 ( textView binding 같은 )
`onDettach()` : 마지막 떨어질 때

## 기타

- 예전에는 바텀네브바가 아니라 탭매니져라는 친구를 사용했었음

- Jetpack이 있는 이유 ( 계속 업데이트 되는 API, 데프리케이트 된거 새걸로 고쳐서 썼으니, android studio가 책임을 가지고 하위 호환은 만들어준다!)

- JetPack : `최근에 추가된 라이브러리`

- `minSDK 21` : 21버전에서도 내 apk는 잘 돌아가야 합니다. 라는 뜻

- support 7? support 4?

- `@JvmStatic`? : Java에서 호출하기 위해서 Companion을 자연스러운 java static으로 바꿔주는 어노테이션입니다.

- viewBinding을 `Fragment`에서 사용할때는 `Activity`와 다르게 선언하는 위치를 한번 더 생각해야 한다. ( 공식 문서 참고 )

- Fragment Life Cycle이 위치가 바뀐 것도 있고 ( API28 이후로 위치가 또 바뀜 )

- Activity안에서 다수의 Fragment가 동시적으로 생성되고 사라짐을 반복하며, Bundle도 유지하기 때문에 memory leak, 변수 전달 에러, 오동작, 등의 문제가 계속해서 발생한다.

- 어려운게 맞는데, 왜 어려운지는 알아야 한다!

- 뒤로가기에 원래 반영이 안되는데, 백스택에 넣는 방법도 있다! ( BackStack )
