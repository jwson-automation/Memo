# View

[TextView](#autocompletetextview)
[FloatingActionButton](#fab)
[ScrollView](#scrollview)
[Spinner](#spinner)
[ViewStub](#viewstub)
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

### 람다식 사용한 함수와, 그냥 함수

여기에서 changeListener를 자동완성해주는걸 보면

{}와
(){}로 나누어지는데

각각의 메소드를 살펴보면 더 쉽게 표현하기 위해서 2개의 표현방법을 모두 android studio에서 추천해준다.

## ViewStub

껍데기를 미리 만들고 나중에 렌더링 하는 녀석 입니다.
: 리소스 로드를 지연시켜서 렌더링을 향상시킵니다.

복잡한 레이아웃일때 lazy 처럼 사용이 가능합니다.

### 코드

```Kotlin
binding = ActivityViewStubBinding.inflate(layoutInflater).apply {
            mainBtnShow.setOnClickListener {
                mainStub.visibility = View.VISIBLE
            }

            mainBtnHide.setOnClickListener {
                mainStub.visibility = View.GONE
            }
        }

```

## Include

그냥 xml코드 재사용할때 쓰는거임

`<include>`

## RequestFocus

처음부터 해당 지점에 커서가 가 있도록 할 수 있습니다.

`currentFocus`

### 코드

```kotlin
fun toggleView() {
        if (currentFocus?.visibility == View.VISIBLE) {
            Toast.makeText(this, "invisiable~", Toast.LENGTH_SHORT).show()
            currentFocus?.visibility = View.INVISIBLE
        } else {
            binding.mainEt.visibility = View.VISIBLE
            Toast.makeText(this, "visiable!!", Toast.LENGTH_SHORT).show()
            binding.mainEt.requestFocus()
        }
    }
```

## DrawerLayout

오늘 중요한 것

서랍!

왼쪽에서 스윽 열리는 친구를 말합니다.

### DrawerLayout 코드

header와 menu를 넣어줄 수 있습니다.

```xml
<com.google.android.material.navigation.NavigationView
        android:id="@+id/navigation_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:layout_gravity="start"
        android:background="#CCCBCB"
        android:orientation="vertical"
        app:headerLayout="@layout/nav_header"
        app:menu="@menu/drawer"
        app:itemTextColor="#000000"/>
```

### header 코드

그냥 머리입니다.

```xml
<TextView
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:text="Logo"
        android:layout_marginStart="8dp"
        android:textSize="24sp"/>
```

### menu 코드

```xml
<?xml version="1.0" encoding="utf-8"?>
<menu xmlns:android="http://schemas.android.com/apk/res/android">
    <item
        android:id="@+id/item1"
        android:icon="@drawable/ic_baseline_home_24"
        android:title="item1"
        />
    <item
        android:id="@+id/item2"
        android:icon="@drawable/ic_baseline_format_list_bulleted_24"
        android:title="item2"
        />
    <item
        android:id="@+id/item3"
        android:icon="@drawable/ic_baseline_chat_24"
        android:title="item3"
        />
</menu>
```

### Activity 코드

레이아웃 단위에서 openDrawer 해주면 됩니다.

```kotlin
binding.mainInclude.mainBtn.setOnClickListener{
            binding.mainDrawerLayout.openDrawer(Gravity.LEFT)
        }
```

각각의 Item 선택 시 동작을 넣어주면 됩니다.

```kotlin
binding.navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item1 -> {
                    Toast.makeText(this,it.itemId.toString(),Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item2 -> {
                    Toast.makeText(this,it.itemId.toString(),Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.item3 -> {
                    Toast.makeText(this,it.itemId.toString(),Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    true
                }
            }
        }
```

### 햄버거 가져와주기

```kotlin
 supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.hamberger_menu)
```

### 햄버거 선택하기

```kotlin
override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            android.R.id.home -> {
                if (binding.mainDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                    binding.mainDrawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    binding.mainDrawerLayout.openDrawer(GravityCompat.START)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
```
