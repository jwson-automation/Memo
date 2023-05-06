# class 준비
전체 특징 장점 단점
1. ViewModel
- 일반 ViewModel과 MVVM 뷰모델의 차이점
![](2023-05-06-23-58-31.png)
![](2023-05-07-00-02-39.png)

- ViewModel을 초기화 하는 여러가지 방법( Provider, Factory, Fragment-ktx )
![](2023-05-07-00-00-04.png)
![](2023-05-07-00-00-54.png)
![](2023-05-07-00-01-22.png)

- ViewModel 금지사항 ( Activity 넣지마세요 )
![](2023-05-07-00-02-23.png)

2. DataBinding
- 데이터 변경을 찔러주는 방법이 3가지 (Observerble, collection, ... )
![](2023-05-07-00-03-21.png)
![](2023-05-07-00-11-15.png)
![](2023-05-07-00-11-38.png)
- Java에서 기본적으로 데이터가 변경되는 Observable이 JDK 초반부터 있었습니다.
- xml 바깥에 layout을 만들고 Data와 Variable을 만들어서 사용했습니다.
- xml에서 자바를 부를 때, annotation, bindingAdapter를  사용해야 했습니다.
- 

3. LiveData
- MutableLiveData 와 LiveData가 있었습니다.
![](2023-05-07-00-13-18.png)
- MainThread일경우 setValue, 아니라면 postValue
![](2023-05-07-00-13-40.png)
- 화면과의 LifeCycle이 맞기 때문에 onActive, onInActive라는 fun으로 동작했습니다.
![](2023-05-07-00-13-54.png)
- onActive와 onInavtive가 계속 동작하는게 아닌, onActive에서만 동작하지만, forever를 사용해서 계속 관찰이 가능했습니다.
![](2023-05-07-00-14-13.png)

4. LifeCycleScope
- LifeCycleScope, ViewModelScope 라는것을 사용하면 쉽게 정리되었습니다.

5. Room
![](2023-05-07-00-15-06.png)
- SQLite DB를 한번 감싸둔 친구, @어노테이션 기반으로 동작했습니다.
![](2023-05-07-00-15-34.png)
![](2023-05-07-00-15-45.png)
![](2023-05-07-00-16-01.png)
- 어떤 @어노테이션이 어떤 역할을 하는지는 알아둘 필요가 있습니다. ( 암기까지는 아니더라도 이해할 수 있어야 합니다. )
- [MVVM + repo] repository를 사용했었습니다. 
- Repository는 싱글톤으로 만들고, DB와 NetWork를 둘 다 레포에서 관리하는게 좋은 구조라고 권장했었습니다.
![](2023-05-07-00-16-29.png)
![](2023-05-07-00-16-42.png)
- Entity, DAO, Database를 만들었었는데, DatabaseBuilder라는 걸 사용해서 만들었었습니다.


6. Compose
![](2023-05-07-00-17-46.png)
- @Composable을 이용해서 대문자로 선언을 했었습니다.
![](2023-05-07-00-18-08.png)
![](2023-05-07-00-18-15.png)
![](2023-05-07-00-18-30.png)
- member variable은 모니터링 대상이 안되기 때문에, mutable, 그리고 remember로 감싸서 사용했습니다.
- expended . value , 를 사용했었는데 위의 리멤버를 사용할때 by를 사용 = property delegation이 있었고, destructuring이라는 방법이 있었습니다.
![](2023-05-07-00-18-47.png)
![](2023-05-07-00-18-56.png)
![](2023-05-07-00-19-06.png)
![](2023-05-07-00-19-44.png)
![](2023-05-07-00-19-52.png)

- Scaffold의 구조나 어떤 사용 방법인지는 대략적인 이해가 필요합니다.
![](2023-05-07-00-20-14.png)
![](2023-05-07-00-20-23.png)

7. SAA
- bundle, Safeargs의 두가지 전달 방법이 있었습니다. 하지만, SAA에서는 safeArgs를 권장합니다.
![](2023-05-07-00-20-47.png)
![](2023-05-07-00-21-00.png)
![](2023-05-07-00-21-09.png)
![](2023-05-07-00-21-17.png)
- NavHost와 NavController를 이용해서 화면 이동을 했습니다.
![](2023-05-07-00-21-32.png)