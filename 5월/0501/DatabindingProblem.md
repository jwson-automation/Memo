# 단방향과 양방향

## 실습 1. xml - java 바인딩

xml -> java 코드를 불러서 보여준다.

1. bindingAdapter를 만든다.
2. view.setImageDrawble 해준다.

---

## 실습 2. Observable을 이용한 DataBinding

1. Dto에 Baseobservable()를 상속합니다.

_`notifyPropertyChanged()`가 핵심입니다._
: 값이 바뀌면, xml에게 다시 그려주라고 요청합니다!!!!!!!!

```kotlin
class YoutubeDto(val image: Drawable, val title: String) : BaseObservable() {

    @get:Bindable
    var clicked: Boolean = false
        set(value) {
            field = value
            notifyPropertyChanged(BR.clicked)  // BR : Binding된 Resource ID가 관리되는 Class
        }

    fun onClickListener(view: View) {
        Toast.makeText(view.context, "Clicked: $title", Toast.LENGTH_SHORT).show()
        clicked = when(clicked){
            false -> true
            true -> false
        }
    }
}
```

2. xml에서 삼항 연산자를 추가해줍니다.

```kotlin
<LinearLayout
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:paddingLeft="10dp"
        android:paddingRight="10dp"
        android:paddingTop="15dp"
        android:paddingBottom="15dp"
        android:onClick="@{youtubeDto::onClickListener}"
        android:background="@{youtubeDto.clicked ? @color/black : @color/white }">
```

## 실습 3. 텍스트 입력의 데이터 바인딩

`TextWatcher` > `Observer`

1. Dto 생성 + 옵저버
   `세터 만들기`

   ```kotlin
   set(value) {
        field = value
        Log.d(TAG, ": $value")
    }
   ```

2. 액티비티 바인딩

```kotlin
binding.textDto = TextDto()
```

3. xml 수정

```xml
<!-- android:text="@{textDto.name}" -->
<!-- 아래 주의! 넣어줄때는 =을 넣어줘야 합니다. -->
android:text="@={textDto.name}"
```

4. 값이 바뀔때마다 세터가 호출됩니다.

5. 어노테이션을 달아줍니다. (게터를 추가해줍니다.)

```kotlin
class TextDto : BaseObservable() {
    @get:Bindable
    var name: String = "Hello"

    set(value) {
        field = value
        Log.d(TAG, ": $value")
        notifyPropertyChanged(BR.name)
    }
}
```
