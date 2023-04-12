# Fragment

Activity가 아닌 Frament를 써야 하는 이유?

: Activity 위에 올라가는 화면의 일부, 부분화면만 바꾸고 싶습니다. (특히 바텀네브바에서)

1. Activity처럼 Fragment도 별개의 생명주기가 있습니다.

먼저, Activity에서는

비정상적으로 종료될 수 있기 때문에, `onSaveInstanceState(Bundle)`를 사용 가능합니다.

`onRestartInstanceState(Bundle)`에서 `onCreate(Bundle)`의 번들을 수정해줍니다.

2. Fragment는 화면이 바뀔 때 데이터 전송은 어떻게 하면 될까요?

이건 내일 할겁니다!

## 기타

- 예전에는 바텀네브바가 아니라 탭매니져라는 친구를 사용했었음
