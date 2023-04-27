# SQLite

## 개요

```
대부분의 Database 99%는 SQLite입니다.
이걸 사용하면 핸드폰 안에 DB를 구축할 수 있게 해줍니다.
RDBMS 데이터베이스, 쿼리문도 SQL문을 사용합니다.
```

### Class

`SQLiteDatabase`
DataBase를 컨트롤 해주는 역할을 합니다.

`SQLiteOpenHelper`
DataBase를 각각의 휴대폰에서 열어주고 생성해줘야 하잖아요?
그 역할을 해줍니다.

`ContentValues`(선택적 사용)
key : Value 구조를 사용할 때 도와줍니다.

`Cursor`
DataBase에서 DB를 조회했을 때, 천만 건이 나왔다면
테이블 형식에서 특정 위치를 포인팅하고, MoveToFirst, MovoToNext...
테이블 커서의 위치를 옮겨주는 것을 도와줍니다.

```kotlin
moveToNext()
moveToFirst()
getColumnIndex()
```

### 연동하기

`DBHelper.kt`

```
1. SQLiteOpenHelper를 상속받습니다.
2. DBHelper를 이용해서 CRUD 생성합니다.
```

### 실습하기

App Inspection을 사용하면 DataBase를 열어볼 수 있다.
(JetPack을 사용하면 App Inspection에서 수정이 실시간으로 적용되게도 가능합니다.)

```
1. DBHelper.kt라는 CLASS를 만듭니다.
2. SQLiteOpenHelper(context, name, factory, version)를 상속합니다.
3. onCreate와 함께 db를 생성해줍니다. (Create Table)
4. onUpgrade로 Table을 업그레이드해줍니다. (DROP and Create)
5. onOpen에서는 dp를 열어줍니다.
6. CRUD 기능을 생성해줍니다. (insert, list, update,delete, select)
```

### 실습참고

```
1. 사이즈가 커지면 transaction을 사용해볼 수 있습니다.
2. 메소드 호출이 아닌 SQL문을 사용해서 구현할 수도 있습니다.
3. `listBtn.performClick()`을 사용하면 다른 버튼의 기능을 함께 사용해 줄 수 있습니다.
```

### 주의사항

```
Alert Table의 onUpgrade 실수하면 수정 불가능합니다. ( 아주 위험 )
DB가 정말 날아갑니다.

예 : DROP하고 Create를 안했다. -> 야! 서버 어디갔어? -> 네?
```
