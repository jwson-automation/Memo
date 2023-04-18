# View

[TextView](#autocompletetextview)
[FloatingActionButton](#fab)
[ScrollView](#scrollview)
[Spinner](#spinner)
[Include](#include)
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

## Include

## Spinner

## DrawerLayout
