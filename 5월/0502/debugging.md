# 디버깅

데이터 바인딩을 할때, 아래와 같이 Binding Adapter를 생성해줘서 형태를 변환하는 것을 만들어줘야 합니다.

```
@BindingAdapter("imageUrl")
fun loadImage(view:ImageView, src:String){
    val packageName = view.context.packageName
    val redId = view.resources.getIdentifier(src,"drawable",packageName)
    view.setImageResource(redId)
}
```
