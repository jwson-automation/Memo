# 정리

1. 03/27 - 1차 기술 면접
2. 03/28 - 알고리즘 수업
3. 03/31 - 산책로 앱 1차 완성

## 면접 준비

- [o] 모의 면접 해보기 ( 3 / 5 )
- [o] 일본 예상 면접 문제 스크립트 만들기
- [] DENA,inc 회사 홈페이지 정독하기
- [] Mobile, Server 관련 말할 수 있는 부분과 말할 수 없는 부분 분리하기

### 예상 평가지

- IT에 관심을 많이 가지고 있는가?

- 지속적으로 성장 가능한 인재인가?

- 동료로써 함께 일함에 불편함이 없는가?

- 스스로 문제를 해결 할 수 있는 능력이 있는가?

## 중요 질문

### 동기 vs 비동기

```
동기 (프리랜서) :
    - 사장 : 요청받은 일을 열심히 해서 줌
비동기 (회사) :
    - 요청받는 사람
    - 요청받은걸 실행하는 사람들
    - 결과를 전달하는 사람

따라서 너무 쉬운 작업을 회사 형식으로 만들면 오히려 작업량이 늘고, 많은 작업량을 프리랜서에게 맡기면 엄청 늦게 도착한다.

그래서 프리랜서형으로 프로그램을 만든 뒤에, 회사형으로 바꿔야 한다.

[ Back-end(server) ]

    : Back-end 에서는 비동기 처리가 성능 향상에 도움을 준다.

    1. 만약, REST API가 구현된 서버가 하나 있다고 했을 때, 당장 해당 서버를 동기로 만들어도 큰 문제는 발생하지 않는다.
    2. 하지만, 대량의 작업이나 여러 유저가 한번에 들어왔다면 서버가 동기로 요청을 처리하고 있다면 REST API의 반응 속도가 느려진다.
    3. 따라서, 비동기 처리를 통해서 효율을 높여줘야 한다.

[ Front-end(Native, Web) ]

    :Front-end 에서는 비동기 처리가 성능 향상 뿐만 아니라, 유저 경험 향상에 도움을 줘서 더욱 중요하다.

    1. 예를 들어, 인스타그램에서 유저가 이미지나 동영상을 불렀다면, 불러오는 과정을 비동기로 처리한다.
    2. 유저는 부른 데이터의 샘플을 바로 볼 수 있고, 글을 쓰고나면 이미지는 이미 완전한 상태로 업로드 되어 있다.
    3. 글을 업로드 버튼을 누르면 포스팅이 시작되며, 그 동안 나는 다른 작업을 할 수 있다.
```

## 기술 면접 예상 질문

### Server 기술 면접 예상 질문

- Spring boot vs Java Legacy

```
[Spring boot vs Java Legacy]

`Java Legacy` : Servlet, JSP, MyBatis
`Spring Boot` : Spring JDBC

1.JSP나 Servlet대신에 Thymeleaf같은 템플릿 엔진을 사용하기 때문에 코드가 간결해집니다.

2. Maven, Gradle을 사용하지 않아도 의존성 관리를 자동으로 해줍니다.

3. DB와 Object 사이의 매핑을 @mapping이나 @mapper-scan을 이용해서 쉽게 할 수 있습니다.
```

- MVC 패턴

```
Model - View - Controller
```

- 싱글톤 패턴 ( 캡슐화 )

```
객체, 변수를 만들어서 사용할 때

창고에 넣어두고 필요할 때마다 써야하는데 필요할때마다 뿅뿅 만들어서 쓰면 관리도 안되고 이게 언제 생성된건지 언제 사라지는지도 확인이 힘들다.

그래서 Getter, Setter를 달아서 체계적으로 호출, 변경해주는 것
```

- AOP

```
OOP (Object-Oriented Programming) : 객체지향

AOP 패턴 (Aspect : 관심있는 기술을 분리한 OOP)
: 제가 실습 했을 떄는 인터셉터 기능을 따로 분리해서 사용했습니다.
```

### Mobile 기술 면접 예상 질문

- [4대 컴포넌트]

```
Activity - 화면

Service - 음악을 실행.
: Foreground Service를 이용해줘야합니다.

BroadCase Reciever - 알람매니저

Content Provider - 연락처 정보 연동
```

- Android LifeCycle

```
onCreate - onStart - onResume - onStop - onRestart - onDestroy
```

- Android LifeCycle에서 주의할 점

```
Fragment와 Activity는 화면 회전 시에 Destroy됩니다.

이걸 막기 위해서 LiveData를 사용해서 데이터를 지켜보고 상태를 확인함으로써 해결이 가능합니다.
```

- ListView, RecyclerView

```
둘 다 화면을 목록에 표시합니다.

하지만, RecyclerView가 높은 성능을 보입니다.

리스트뷰는 모든 목록을 표시ㅏ지만, 리사이클러뷰는 `뷰홀더`를 사용해서 뷰의 생성과 파괴를 최소화하기 때문입니다.

거기다 리사이클러뷰는 더욱 다양한 기능도 제공합니다.
```

- ViewPager

```
Fragment - ViewHolder

프래그먼트를 사용하면 단일된 페이지를 커스텀해서 올려주는 것이 가능하고,

데이터 기반의 카드 리스트를 보여주는 것이 가능하다.
```

- SQLite

```
RDBMS(관계형 데이터베이스)
안드로이드의 내장형 데이터베이스
```

- Transaction

```
트랜잭션은 묶어줌을 말합니다.

Trans - action

내가 3개의 명령을 내렸다고 해보겠습니다.

1. 테이블 생성
2. 데이터 추가
3. 데이터 호출

위 3가지를 문제를 실행시켰을때,
만약 '2'번에서 문제가 발생했다고 하면 이미 1번에서 테이블이 생성이 되었기 때문에 해당 생성된 테이블을 다시 삭제해줘야 합니다.

Transaction을 사용하면 1, 2, 3 번중 하나라도 실패하면 1, 2, 3을 모두 취소하는 역할을 해줍니다.

db.beginTransaction();
1. 테이블 생성
2. 데이터 추가
3. 데이터 호출
db.endTransaction();

```

- MVC, MVP, MVVM 패턴

```
MVC
: Model - View 사이의 Controller

MVVM
: Model - View 사이의 ViewModel

MVP
: Model - View 사이의 Presenter
```
