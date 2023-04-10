# 오늘의 디버깅

- val itemTouchCallBack = ItemTouchHelper(adapter.ItemTouchCallBack())

타입 미스매치를 디버깅을 할때, 미리 만들어둔 adapter를 호출하는 것이 아니라,

(1) `adapter.ItemTouchCallBack()`를 해야 하는데,
(2) `MyAdapter().itemTouchCallBack()`을 해주고 있었다.

두개가 디버깅은 되지만 완전히 다른 의미가 되어버리는데

(1) 기존의 어댑터를 가져와서 콜백함수를 호출함
(2) 새로 만들어서 호출함!

정리

```
(1) adapter.ItemTouchCallBack(): 이 코드는 adapter라는 객체의 클래스에 정의된 ItemTouchCallBack이라는 이름의 내부 클래스 또는 인터페이스를 생성하는 것을 의미합니다. 여기서 adapter는 해당 클래스 또는 인터페이스를 포함하는 클래스의 인스턴스입니다.

(2) MyAdapter().itemTouchCallBack(): 이 코드는 MyAdapter라는 클래스의 인스턴스를 생성한 후, 그 인스턴스에서 itemTouchCallBack이라는 메소드를 호출하는 것을 의미합니다. MyAdapter 클래스에는 itemTouchCallBack이라는 이름의 메소드가 정의되어 있어야 합니다.

간략하게 요약하자면, (1)은 특정 객체의 클래스에 정의된 내부 클래스 또는 인터페이스를 생성하는 것이고, (2)는 특정 클래스의 인스턴스를 생성한 후 그 인스턴스에서 메소드를 호출하는 것입니다.
```
