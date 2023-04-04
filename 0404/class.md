# 0404

ToolBar, 다양한 알림

## Toolbar vs Actionbar vs Appbar

- 버전에 따라서 차이가 있습니다.
- 롤리팝까지(Action bar)

### 실습 1. 툴바 생성

1. 일단 기존의 툴 바를 제거합니다. `theme.xml` > `NoActionBar`
2. toolbar를 findViewById로 가져옵니다. `물론 xml에서 수정해도 됩니다.`
3. 메뉴를 추가하고 싶다면, 메뉴를 추가하고 연결합니다. `inflateMenu(R.menu.-)`
4. 메뉴 선택 후 이벤트를 추가합니다. `toolbar.setOnMenuItemClickListener()`
```
[주의!] `onCreateOptonsMenu`와 다릅니다! `onOptionsItemSelected`로 수정할 수 없습니다.
```

### Sample Code
```Kotlin
//toolbar customizing
        toolbar = findViewById(R.id.toolbar)
        // 이렇게 나타내는게 Basic 이고
//        toolbar.title = "hello,world!"
//        toolbar.background = resources.getDrawable(R.color.teal_200, theme)

        // 이렇게 apply로도 나타낼 수 있습니다.
        toolbar.apply {
            title = "hey"
            background = resources.getDrawable(R.color.teal_200, theme)
            inflateMenu(R.menu.menu_main)
        }

        toolbar.setOnMenuItemClickListener(){
            when(it.itemId) {
                R.id.action_search -> Log.d(TAG, "onCreate: search")
                R.id.action_setting -> Log.d(TAG, "onCreate: setting")
                else -> Log.d(TAG, "onCreate: else")
            }
            true
        }
```

### 실습 2. 따라 움직이는 툴바 생성

1. xml 파일이 툴바와 함께, 화면에 다 나오지 않는 커다란 NestedScrollView를 만듭니다.
2. `layout_behavior` 옵션을 이용하면 스크롤과 함께 툴바가 움직이게 해줍니다.
3. 