# kotlin에서 "ArrayAdapter"는 뭐야?

`ArrayAdapter`는 `ListView`나 `Spinner`와 같은 `AdapterView`와 함께 사용되는 `Array`나 `List`와 같은 데이터 소스를 표시하기 위한 표준 `Array` 어댑터입니다. 

## 사용법
`ArrayAdapter`는 다음과 같이 생성하고 설정할 수 있습니다.
```kotlin
val array = arrayOf("항목1", "항목2", "항목3")
val adapter = ArrayAdapter(context, android.R.layout.simple_list_item_1, array)
listView.adapter = adapter
```

## 생성자 매개변수
ArrayAdapter 생성자는 다음과 같은 매개변수를 가집니다.
```kotlin
ArrayAdapter(context: Context, resource: Int, objects: Array<T>)
```

context: 어댑터를 사용하는 컨텍스트입니다.

resource: 항목 레이아웃 리소스 ID입니다. 예를 들어, android.R.layout.simple_list_item_1을 사용하면 간단한 텍스트 뷰로 구성된 항목 레이아웃을 사용할 수 있습니다.

objects: 표시할 데이터 소스입니다.