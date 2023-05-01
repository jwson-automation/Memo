# JetPack

: 모든 Android 버전에서 일관되게 작동하는 코드 작성을 도와주는 라이브러리

- JetPack 이전 : Spport Library V4, V7

- 전에는 4가지의 컴포넌트로 JetPack을 구분했었습니다. ( 이미지 참고 )

1. Architecture
   Data Binding, Lifecycle, `LiveData`, Navigation, Paging, `Room`, `ViewModel`, WorkManager

2. UI
   Animation & Transitions, Auto/ TV& Wear, Emoji, Fragment, Layout, Palette

3. Foundation
   AppCompat, Android KTX, Multidex, Test

4. Behavior
   Download Manager, Media & PlayBack, Permission, Notifications, Sharing, Slices

저희는 이중에서 `DataBinding`, `LiveData`, `Navigation`,`Room`,`ViewModel`, 등을 배울 것입니다.

## 기타

### batch process

항상 모든 유저의 동작에 매번 큰 수의 계산을 할 수 없기 때문에 `batch job`을 해줘야 합니다.

주기적으로 정해진 시간이 동작(계산)을 시키는 것으로 유저 경험을 개선 시킵니다.

이런 서버의 배치 프로세스와 동일한 행동을 안드로이드에서 가능하도록 도와주는 라이브러리가 `WorkManager`입니다!
