# Fragment 통신

Android JetPack의 ViewModel, LiveData를 사용하는 방법을 뒤에서 배울 것이지만,

오늘은 InterFace를 활용해서 Bundle로 전달하는 것을 먼저 연습할 것 입니다. ( 실제로도 수요가 있습니다.)

## 통신

Fragment끼리 직접 통신은 불가능하다. 그래서 Parent(Activity)를 이용해서 통신한다.


### 무지성으로 해보기
1. `requireActivity().count` 부모 액티비티의 `count`를 가져온다.
2. `(requireActivity() as CommunicationActivity).count` 타입 캐스팅을 해야 정상적으로 가져와진다.
3. 아래와 같이 parent Activity의 값을 그대로 가져와서 사용하는거? 된다! 근데 이게 맞을까?
```
binding.plusButton.setOnClickListener(){
            (requireActivity() as CommunicationActivity).count++
            (requireActivity() as CommunicationActivity).binding.countTv.text = "count:${(requireActivity() as CommunicationActivity).count++}"
        }
```

## interface를 사용해보자

`구현체` concrete class
`어나니머스 클래스` = 한번쓰는 1회용 클래스! -> val 1회용 : communicationCallback {..}

1. 인터페이스 생성, 및 구현
```Kotlin
class CommunicationActivity : AppCompatActivity(), CommunicationCallback {
    override fun onPlusCount() {
        count++
        binding.countTv.text="Count:$count"
    }
}
```

2. 해당 인터페이스를 프래그먼트에 리스너로 가져와서 연결!
```Kotlin

class CommunicationFragment : Fragment() {
    lateinit var listener: CommunicationCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.plusButton.setOnClickListener(){
            listener.onPlusCount()
        }
    }
}
```

3. 실제 동작을 누가할까? 를 MainActivty에서 아래와 같이 지정해준다.

```Kotlin
communicationFragment.listener = this
```

4. 이전의 adapter의 SetOnClickListener 때와 동일합니다.

### onAttatch()

Activty의 Context를 가져올때 사용합니다.

아래 같은 형태로 자주 사용합니다.
```
lateinit var act : CommunicationActivity

onAttatch(context:context){
    super.onAttatch(context)
    act = context as CommunicationActivity
}
```

## Bundle을 사용해보자! ( 중요! )

1.`changeFragmentView()` 를 액티비티에 만듭니다.
```kotlin
fun changeFragmentView(fragment: Int, param: String) {
        val transaction = supportFragmentManager.beginTransaction()

        when (fragment) {
            1 -> {
                val frag1 = Fragment1()
                transaction.replace(R.id.fragment_container, frag1)
            }
            2 -> {
                val frag2 = Fragment2()
                transaction.replace(R.id.fragment_container, frag2)
            }
        }
        transaction.commit()
    }
```

2. 하지만! 여기서 보며는 이제 param이 사용됩니다!! 데이터 유실을 방지해줄것입니다!!!!
