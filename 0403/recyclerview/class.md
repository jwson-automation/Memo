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

이제 리사이클러뷰로 넘어갑니다!

hahahahahahahaha

리스트뷰도 많습니다. ListView에 홀더 달아둔게 이상한게 아닙니다.

하지만, 리사이클러뷰라는걸 이제 씁니다.

---

ListView는 개발자가 건드릴게 너무 많다.

그러니까, 우리는 RecyclerView라는걸 만들었다.

---

`BaseAdapter()`라는 친구를 가져와서 어댑터를 만들건데
이걸 상속받아오면,
GetView, GetCount, GetItem, GetItemID 이런 애들을 데려온다.

핵심은 이중에 `GetView`다.

---

## ListView vs RecyclerView

RecyclerView

1. ViewHolder를 반드시 사용해야한다. (RecyclerView.viewHolder)
2. StaggeredGridLayout[크기가 모두 다른 카드 리스트]도 가능하다.
3. ItemAnimator가 존재한다.
4. 커스텀 Adapter를 구현해야 한다.
5. ItemDecoration 객체를 사용해서 구분선 설정이 까다롭다.
6. 개별터치 이벤트를 관리하지만, 클릭 처리가 내장되어 있지 않다. RecyclerView.OnItemTouchListener

---

만드는 방법

1. RecyclerView를 만든다.
2. Adapter를 상속받아와서 만든다.
3. Adapter의 메소드를 3개 만든다.
4. ViewHolder를 만든다.
5. GetView를 메소드를 구현한다.
6. 연결한다.

---

```Kotlin
class MyAdapter(var list : ArrayList<String>) : RecyclerView.Adapter<MyAdapter.CustomViewHolder>() {
        class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
            var name = itemView.findViewById<TextView>(R.id.name)
            // [아래는 추가적으로 만드는 것] private 대신에 아래처럼 setter만들어줌
            fun bindInfo(data:String){
                name.text = data
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
            // view를 생성 -> holder의 파라미터로 넣어줌. 바로 위에 보면 class가 그렇게 생겼잖아
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_row, parent,false)
            // atatchToRoot : <ListView> 여기에 넣을까요? 라는 뜻! </ListView>
            return CustomViewHolder(view)
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            // 데이터와 ViewHOlder를 연결해주는 친구임
            // holder는 위에 만든 CustomViewHolder임

            // 그냥 고치고 싶으면 아래 방법을 사용!
//            holder.name.text = list[position]
            //세터처럼 쓰고 싶으면 아래 방법을 사용!
            holder.bindInfo(list[position])
        }

        override fun getItemCount(): Int {
            return list.size
        }

    }
```

---

event 처리,

View Bind 바꿔보기,

상당히 어려움!

-->

---

event 처리 전에, R.id를 ViewBinding으로 변경
--> 이건 계속해서 반복해보면서 구조를 눈에 익혀야함!

---

`viewholder`에
`View.OnCreateContextMenuListener`를 추가 상속해줘서
이벤트 리스터를 달아주고, 임플리먼츠되어있는 메소드 가져와줍니다.

- `itemView`는 `binding.root`에서 가져오는 최상위클래스가 가지고 있는 것이기 떄문에 어디서든 쓸 수 있습니다.!!!

- // true : 여기서 끝, false : 다음 진행

---

event Listener를 넣어줄건데

Adapter에 넣어주는게 맞을까? [ 어댑터는 보여주는 역할인데...]

이벤트 처리를 이렇게 막 쳐넣어도 되나?

--> 그건 좀 아니잖아

그러니까 Adapter를 외부에서 주입해서 역할을 나눌 수 있습니다.

---
```
.apply {
                Toast.makeText(parent.context,"선택", Toast.LENGTH_SHORT).show()
            }
```
---

+
nested class : default

inner class : 바깥에 있는 class에 엑세스 하기 위해서는 `inner`가 붙어야함
---

