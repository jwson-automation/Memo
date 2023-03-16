# 오늘 알게 된 점

## ListView

ListView는 기본적으로 Kotlin에서 제공하는 기능을 바탕으로 구현해야 한다.

이해하기 어려웠던 게,

처음부터 끝까지 코드로 구현해야 한다고 생각해서

ArrayList의 데이터를 반복문을 돌려서 하나씩 view로 Return 시켜주는 일련의 과정을 직접 구현하려고 했다.

### 기존의 생각

```
1. 먼저 ArrayList에 정보를 담아준다.
2. ArrayList의 정보를 하나씩 꺼낸다.
3. View로 변환해서 ArrayList<View> 안에 넣어준다.
4. 새로운 정보가 추가되거나, 삭제되면 다시 ArrayList<View>를 변환한다.
5. 데이터가 갱신 될 때마다 View를 다시 띄워준다.
6. ....
```

하지만,

실제로는 훨씬 더 간단했다.

### 실제

```
1. ArrayList에 정보를 담아준다.
2. BaseAdapter를 상속받아 어댑터를 만든다.
3. View(XML)과 데이터(List)를 연결해준다.
4. MainActivity에서 ListView.adapter를 연결해준다.
5. 자동적으로 ArrayList를 불러오는 과정이 동작한다.
```

커스텀 여부가 중요한게 아니었다.

그냥 애초에 기본 제공 함수들을 상속받아와서 잘 연결해주면 예쁘게 동작한다.
