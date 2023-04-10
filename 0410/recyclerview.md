# RecyclerView ListAdapter

안드로이드 라이브러리 대표 2가지
글라이드 ( 이미지 가져오기 )
레트로핏 ( Json, Xml 파싱을 위한 네트워크 통신 )

[Lsit Adapter](#list-adapter)

## List Adapter

- 먼저 RecyclerView에서 ContextMenu는 짜는게 조금 복잡합니다, 그래서 스와이프 삭제를 구현해 볼 예정입니다.

- `notifyDataSetChanged()`를 호출을 해줘야 했었습니다. `noseche`에 마우스를 올리면 recommended가 나오는데, 1개 지웠는데 모든 데이터 체인지를 확인하는게 불필요하다고 말한다. 이 말은 특정 위치에 값의 변경을 알려주기 위한 다양한 `notify`가 존재한다는 것!

- 하지만, 우리는 좀 더 빠르게 비교하기 위해서 `DiffUtil`을 쓸겁니다! 하나씩 위치를 정해주는게 아니라, 그냥 list를 두 개 만들어서 변화여부를 보고 바로 변경 지점을 확인, 수정해준다. ( wow good ) ( 심지어 비동기임 async )

- 이 `DiffUtil`을 사용하는게 바로 `ListAdapter`입니다.

### DiffUtil

`DiffUtil.ItemCallBack`

- are Items The Same?
  : oldItem과 newItem의 해쉬코드를 == 비교해줌
  : 값 비교

- are Contents The Same?
  : oldItem == newItem 을 == 비교해줌
  : 오버로딩된 equeals 비교

--> 같은 아이템이냐 + 값이 같냐?
--> list를 생성자로 받을 필요가 없어진다.
--> getItemcOunt가 필요 없어진다.
--> getItem(position) 이 제공됩니다.

```kotlin
class MyAdapter: ListAdapter< String , MyAdapter.CustomViewHolder>(StringComparator){
        companion object StringComparator : DiffUtil.ItemCallback<String>() {

            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem.hashCode() == newItem.hashCode()
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
                // oldItem.equals(newItem)을 위와 같이 표현함
            }

        }
```

- 컴패니언 오브젝트가 아니라 private fun으로 해놔도 상관 없습니다.
- ListAdapter ( 여기 DiffUtil의 두 메소드를 넣어주기만 하면 오케이 )

```kotlin
// 이 친구가 기본적으로 구현되어 있기 때문에, 아래에 바로 넣어주면 됩니다.
override fun getItemCount(): Int {
            return super.getItemCount()
        }

        override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
            holder.apply{
                //getItem : 위치의 값을 가져옴.
//                bindInfo(myList[position])
                bindInfo(getItem(position))
            }
        }
```

이제 데이터를 아래와 같이 넣어줍니다.

```kotlin
before --
val data = COUNTRIES
adapter = MyAdapter(data)

after --
// submitList로 데이터를 제공한다.
// 기존의 목록과 새로운 목록을 비교하기 때문에, 두 목록의 reference는 달라야 한다. toMutableList로 새로 생성.
val data = COUNTRIES
adapter.submitList(data.toMutableList())
```

삭제되면 새로운 List를 만들어서 DiffUtil에 던져주면

DiffUtil이 `오 이거 바뀐건데? 내가수정함 ㅋㅋ` 해줌

```
adapter.deleteListener = object : MyAdapter.DeleteListener {
            override fun delete(position: Int) {
                Log.d(TAG, "delete: $position")
                data.removeAt(position)
//                adapter.notifyDataSetChanged() //필요없어짐.
                //data 의 reference가 바뀌어야 ListAdapter가 워킹함.
                adapter.submitList(data.toMutableList())
            }
        }
```

###
