# View

[TextView](#autocompletetextview)
[FloatingActionButton](#fab)
[ScrollView](#scrollview)
[Spinner](#spinner)
[DrawerLayout](#drawerlayout)

## TextView

    텍스트뷰

### AutocompleteTextView

    자동완성 기능을 가지고 있습니다.

### MultiCompleteTextView

    여러가지 자동완성 기능을 가지고 있습니다.

### TextInputLayout

    그냥 레이아웃입니다.

    테마, 숫자제한, 카운터 설정 가능

    + `addTextchangedListener`

## FAB

쇼로록 나오는 친구를 만들어 볼 예정입니다.

### FAB Animation

```Kotlin
var isOpen = false
private fun toggle() {
        binding.fabItem1.animate().translationY(if (isOpen) 0f else -250f)
        binding.fabItem2.animate().translationY(if (isOpen) 0f else -500f)
        binding.fab.animate().rotation(if (isOpen) 0f else -360f)
        isOpen = !isOpen
    }
```

## ScrollView

`스크롤뷰안에 리사이클러뷰가 들어있을 수 있습니다.` 이 경우에는 어떻게 동작할까요?

### ScrollView + RecyclerView = > NestedScrollView

여기에서 원하지 않는 스크롤이 동작할 수 가 있어서, `nestedScrollView`를 사용합니다.

nestedScrollView를 사용하면 조절이 가능해집니다.

## Spinner

여러 후보중 하나를 선택하는 콤보박스 스타일 위젯

### 코드

spinner activity

## DrawerLayout
