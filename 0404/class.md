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

### 실습 2. 스크롤과 따라 움직이는 툴바 생성

1. xml 파일이 툴바와 함께, 화면에 다 나오지 않는 커다란 NestedScrollView를 만듭니다.
2. `layout_behavior` 옵션을 이용하면 스크롤과 함께 툴바가 움직이게 해줍니다.
3. `layout_scrollFlag` 옵션을 이용하면, 천장에 붙은 툴바 혹은 내리면 내려오는 툴바로 설정이 가능하다. `설명하는게 재밌다`

### 실습 3. Collapsing 툴바 ( 접히는 툴바 )

1. 기존의 툴바를 CollapsingToolBarLayout에 담는다.
2. AppBarLayout에 넣는다.
3. 펼쳐졌을 때와 접혀졌을때를 구분해서 만들어 주면 됩니다.
4. 애니메이션도 넣어주면 됩니다.

```
 `layout_collapseMode`를 이용해서 메뉴 아이템도 지워줄 수 있습니다.
 `layout_collapseParallaxMultiplier`를 이용해서 변화 속도를 줄 수 있습니다.
```

## Custom View
화면에 보이는 것은 모두 View의 자식, 서브클래스 입니다.
직접 뷰를 만들어 보겠습니다. ( 게임 같은거 만들 때 필요합니다. )

- View에서 상속받아와서 생성합니다.
- 모든 화면의 View는 항상 Context가 있습니다. ( 어디에 붙어 있는지 알아야함 )
- AttributeSet을 매개변수로 하는 생성자를 구성합니다. ( 바꾸고 싶은 것들을 Attribute로 세팅한다.)

### 실습 4. Custom View
1. Layout을 가져옵니다.
2. 생성자에 Context와 AttributeSet을 넣어줍니다.
3. init을 해줍니다. (findViewById...)
4. 데이터 타입은 `attrs.xml`에 선언해줍니다.

5. getAttrs를 해줍니다.... 


### 샘플코드
```Kotlin
class CustomNameCard : ConstraintLayout {
    lateinit var iv: ImageView
    lateinit var name: TextView
    lateinit var org: TextView

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_name_card_view, this, false)
        addView(view);
        iv = findViewById(R.id.user_img_iv)
        name = findViewById(R.id.userName)
        org = findViewById(R.id.user_org_tv)
    }
    // attrs.xml 파일로부터 속성 정보 확보 - typedArray
    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomNameCard)
        setTypedArray(typedArray)
    }
    // 속성값을 view 요소들에 연결
    private fun setTypedArray(typedArray: TypedArray) {
        name.text = typedArray.getText(R.styleable.CustomNameCard_userName)
        org.text = typedArray.getText(R.styleable.CustomNameCard_userOrg)
        iv.setImageResource(
            typedArray.getResourceId(
                R.styleable.CustomNameCard_userImg,
                R.drawable.ic_launcher_foreground
            )
        )
        typedArray.recycle()
    }
}
```
