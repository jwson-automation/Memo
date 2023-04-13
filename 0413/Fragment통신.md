# Fragment 통신

Android JetPack의 ViewModel, LiveData를 사용하는 방법을 뒤에서 배울 것이지만,

오늘은 InterFace를 활용해서 Bundle로 전달하는 것을 먼저 연습할 것 입니다. ( 실제로도 수요가 있습니다.)

## 통신

Fragment끼리 직접 통신은 불가능하다. 그래서 Parent(Activity)를 이용해서 통신한다.

1. `requireActivity().count` 부모 액티비티의 `count`를 가져온다.
2. `(requireActivity() as CommunicationActivity).count` 타입 캐스팅을 해야 정상적으로 가져와진다.
3. 아래와 같이 parent Activity의 값을 그대로 가져와서 사용하는거? 된다! 근데 이게 맞을까?
```
binding.plusButton.setOnClickListener(){
            (requireActivity() as CommunicationActivity).count++
            (requireActivity() as CommunicationActivity).binding.countTv.text = "count:${(requireActivity() as CommunicationActivity).count++}"
        }
```
