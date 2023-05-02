# Room

룸!

SQLite를 추상화한 객체이자 ORM에 해당됩니다.

`테이블을 보는게 아니고 DTO만 봅니다.`

테이블을 보는거랑, json형식 / class 형식만 보는거랑 다를게 있나요?

나는 SQL, Excel은 모르겠습니다. 그냥 데이터 변경을 요청할테니 알아서 넣어주세요!

`SQL문 안보이게 치워주세요`
서버에서는 같은 역할을 하는 hybernate라는 게 있습니다.

SQL 테이블 대신해주세요 ~

교수님 말하시길 : SQL을 잘 하는 사람은 쓰다가 답답해서 화나는 경우가 있다.

## 역사

1. entity 빈이라는게 있었는데 효율이 안나옵니다. 성능이 구립니다. 그래서 포기했습니다 사실

2. 그런데, hybernate가 열심히 해서 살아남았습니다.

3. app인 경우에 enterprise app이라고 해도 데이터를 그렇게 많이 저장하지 않습니다. 근데 hybernate가 잘 버티고 있다? 오? 저거 안드로이드에도 넣자!

## 주요 컴포넌트

- Entity
  : 테이블
- DAO
  : 데이터베이스에 접근하는 메소드들을 넣어둔 오브젝트
  : select만 sql로 짤 수 있게 해뒀습니다. ( select는 그냥 sql이 더 쉬움 솔직히)
- Database
  : DAO 객체를 제공하여 데이터베이스를 이용 가능한 Access point

## JetPack

ViewModel, LiveData와 같이 쓰이도록 설계되어 있으며

DB 접근을 위한 라이브러리인 만큼 Coroutine을 기반으로 동작합니다.

`Repository`라는 친구가 중간 관리를 도와주는데, 이 친구가 한번만 생성이 필요합니다. -> Retrofit처럼!

컴포넌트 3개를 관리해주는 Repository!

### 기타

Retorofit은 Dispatcher.Main으로 줘도 알아서 여러 쓰레드에서 일을 잘하는 친구입니다.

ROOM도 똑같습니다. DB관리인데 메인 쓰레드를 사용하면 좋은 사용 방향이 아님으로 코루틴과 함께 으쌰으쌰 잘합니다.

ROOM 이전에 RealM이라는 친구가 있었습니다.
https://velog.io/@jojo_devstory/Android-Realm%EC%9D%B4%EB%9E%80-SQLite%EB%B3%B4%EB%8B%A4-%EC%A2%8B%EB%8B%A4%EB%8D%98%EB%8D%B0
