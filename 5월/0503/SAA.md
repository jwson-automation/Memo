# SAA

Single Activity Architecture

하나 또는 적은 수의 activity를 사용합니다.

Fragment를 주로 사용합니다.

Navigation을 사용해서 유연하게 구성합니다.

## 의존성 추가

navigation-fragment-ktx
navigation-ui-ktx

## Naviagtion 구성 요소

- Navigation Graph

- Navigation Controller

- Navigation Host

## 코드 읽기

네베게이션에서 드래그로 연결해주고,

`Navigation.findNavController().navigate` 아래 코드처럼 코드를 작성해주면 끝입니다.

네비게이션의 `arguments`를 이용해서 데이터를 전달하고,

`Deep Links` 를 이용해서 외부접속을 허용해줄 수 있습니다.
(pending Intent가 가능하게 하는 용도로도 사용합니다.)

## 코드

```kotlin
class TitleScreen : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_title_screen, container, false)

        view.findViewById<Button>(R.id.play_btn).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_title_screen_to_register)
        }
        view.findViewById<Button>(R.id.leaderboard_btn).setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_title_screen_to_leaderboard)
        }
        return view
    }
}
```

```kotlin
class GameOver : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_over, container, false)

        view.findViewById<View>(R.id.play_btn4).setOnClickListener {
            Navigation.findNavController(view).navigate(R.id.action_game_over_to_match)
        }
        return view
    }
}
```
