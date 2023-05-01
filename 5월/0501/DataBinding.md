# DataBinding

: 뷰 바인딩의 확장판 느낌!

호불호가 갈립니다!

추적이 어렵습니다!

Kotlin에서 Layout을 바꿀 수 있고, Layout에서 Kotlin의 데이터를 직접 참조가 가능합니다. wow!

빨갛게 고장날때마다 코끼리 자주 눌러주면 됩니다.

## 실습

0. 세팅

Kotlin annotation Processing Tool 플러그인 사용 설정

`layout`이 감사져 있으면 viewBinding은 필요가 없습니다.

```
plugins{
    id 'org,jetbrains.kotlin.kapt'
}
```

```
viewBinding{
        enabled = true
    }
    //Databinding 설정하기
dataBinding{
        enabled = true
    }
```

1. layout을 xml로 감싸줍니다.
2. <data>로 사용할 데이터를 kotlin에서 가져옵니다.
   `{user.firstName}`과 같은 형태로 dto의 데이터를 집어 넣습니다.
3. Activity에서 `binding.user = userList[Random.nextInt(3)]`을 넣어주면 바인딩 된 유저값을 변경시킬 수 있다.

-

4. <variable>로 이제 MainActivityWithDataBinding을 변수로 받아준다.
5. `activity::onButtonClick`을 그냥 받아서 `android:onClick`에 연결시켜 버릴 수 있다!

-

여기에서 삼항 연산자 등을 이용해서 더 다양하게 표현할 수 있다.

여기에서 완전히 새로 배워야 하는 문법이 추가됨으로, 호불호가 여기에서도 갈린다!

`expression_language` el을 배워야 합니다~!
