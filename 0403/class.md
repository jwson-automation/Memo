# 0403 수업

```
[kotlin]companion = [java]static
```

## ListView vs RecyclerView

ListView에 넣어주는 역할이 일단 어댑터

```
Adapter

- GetView로 순서대로 정보를 가져와주고 [ Title, Description, img, ... ]

- XML로 바꿔준다. ( inflate )

```

view라는 타입은 코틀린의 최상위 클래스임

inflate시켜서 가져다 붙일건데,

getAdapter
setAdapter
.adapter

setter getter --> 그냥 . adapter로 가능합니다.

---

## 화면에서 사라지면 어떻게 할까요?

--> 이게 바로 ListView와 RecyclerView의 핵심 ConvertView, Holder, ....

---

ListView 에서 화면에서 사라지는 것들을 재사용하기 위해서 `ConvertView : View?` 물음표가 달려있음

ListView에서도 사실 재활용을 한다. null인 경우와 null이 아닌 경우를 비교해서,

근데, `findViewById` 할 일이 많아진다면 어떻게 하나?

이걸 최초에 ListView 만들 때, 고민을 하지 못했다!

엔지니어들은 View의 재활용 할때도 안할때도 계속 다시 Find를 걸어줘야 하는 것이 너무 싫었다!

그래서 `ViewHolder`라는 놈을 만들었따!

`"log에 findViewById 계속 미친듯이 뜨는데, 이거 괜찮나..?"`

그래서

`class ViewHolder`라는 친구를 만들고,

`lateinit var tv:TextView`를 만들어준다.

`holder = ViewHolder()`를 연결해주고

`holder.tv = view.findViewBId(R.id.name)`를 해준다.

`view.tag = holder`라고 태그를 달아준다!

1. view == null 이라면 view.tag를 연결
2. view != null 이라면 태그에 연결되어 있을테니
   `holder = view.tag as ViewHolder`를 해주는 것!!!!

---

정리 하겠다.

View는 최상위 클래스다.

Adabter는 리스트 자료를 순서대로 가져와주는 친구다.

View.adapter라고 하면 setAdapter를 하는 것과 똑같다.

ListView에서 재사용을 잘한다.

하지만, FindViewByID를 너무 자주한다.

그래서 별도의 클래스 ViewHolder라는 걸 만들어줬다.

그래서 거기에 tag값을 저장해주는 걸로 FindViewByID를 한번만 할 수 있도록 해준다.

---

하지만! ViewHolder라는 클래스를 개발자들이 따로 만드는게 정말 안드로이드 프레임워크 입장에서 좋은가?
( 안드로이드 프레임워크는 가능하면 쉽게 개발자들이 통일된 양식에서 개발할 수 있도록 도와주고 싶다!)

--> 그래서 RecyclerView가 나타났다~!!

---

---

### 바인딩은 아래 두 방법이 있다.

```kotlin
lateinit var binding: ActivityMainBinding
...
binding = ActivityMainBinding.inflate(layoutInflater)


val binding: ActivityMainBinding by lazy {
    binding = ActivityMainBinding.inflate(layoutInflater)
    }

```

위는 잘 보면 Activity를 바인딩해서 쉽게 연결해준거다!

아래는 ViewHolder의 ItemList를 바인딩 해주는 코드다!

```kotlin
lateinitvar binding : ItemRowBinding

binding = ItemRowBinding.inflate(layoutInflater)
...

ItemRowBinding.inflate(LayoutIflater.from(parent.context)) 도 가능!

```

---
