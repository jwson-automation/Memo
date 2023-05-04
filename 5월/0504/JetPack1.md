# 실습 1

Room위에 LiveData적용하기

## 코드 ( DAO )

변경 전 DAO

```kotlin
@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getNotes() : MutableList<NotesDto>

    @Query("SELECT * FROM note WHERE ID = (:id)")
    suspend fun getNote(id: Long) : NotesDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(dto : NotesDto)

    @Update
    suspend fun updateNote(dto : NotesDto)

    @Delete
    suspend fun deleteNote(dto : NotesDto)
}
```

1. LiveData로 리턴 타입 변경

```kotlin
@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    suspend fun getNotes() : LiveData<MutableList<NotesDto>>

    @Query("SELECT * FROM note WHERE ID = (:id)")
    suspend fun getNote(id: Long) : LiveData<NotesDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(dto : NotesDto)

    @Update
    suspend fun updateNote(dto : NotesDto)

    @Delete
    suspend fun deleteNote(dto : NotesDto)
}
```

2. suspend를 지워준다.
   : 라이브 데이터를 리턴한다는 것은 지금 당장 결과를 주는 것이 아니라, 옵저브를 사용한다는 것이다. ( 결과를 비동기로 받는 것 )
   : 서스펜드를 사용해서 코루틴에서 호출하는것과 형태가 다릅니다. ( 비동기 함수 호출 )

```kotlin
@Dao
interface NoteDao {

    @Query("SELECT * FROM note")
    fun getNotes() : LiveData<MutableList<NotesDto>>

    @Query("SELECT * FROM note WHERE ID = (:id)")
    fun getNote(id: Long) : LiveData<NotesDto>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(dto : NotesDto)

    @Update
    suspend fun updateNote(dto : NotesDto)

    @Delete
    suspend fun deleteNote(dto : NotesDto)
}
```

3. Repository도 수정해줍니다.

```kotlin
suspend fun getNotes() : MutableList<NotesDto> {
        return noteDao.getNotes()
    }
    suspend fun getNote(id : Long) : NotesDto {
        return noteDao.getNote(id)
    }
```

```kotlin
  suspend fun getNotes() : LiveData<MutableList<NotesDto>> {
        return noteDao.getNotes()
    }
    suspend fun getNote(id : Long) : LiveData<NotesDto> {
        return noteDao.getNote(id)
    }
```

4. ListActivity를 수정해줍니다.

```kotlin
private fun initAdapter(){
        listAdapter = NoteListAdapter()
        CoroutineScope(Dispatchers.Main).launch{
            listAdapter.listData = getData()
            listAdapter.notifyDataSetChanged()
        }
```

```kotlin
private fun initAdapter() {
        listAdapter = NoteListAdapter()
        getData().observe(this) {
            listAdapter.listData = it
            listAdapter.notifyDataSetChanged()
        }

```

룸과 라이브 데이터 사용한다는 것은, 자동으로 Mutable 변경에 대한 그리기를 지원하기 때문에, 아래 리프레쉬는 이제 필요가 없어집니다.

```kotlin
private fun refreshAdapter() {+
        CoroutineScope(Dispatchers.Main).launch {
            listAdapter.listData = getData()
            listAdapter.notifyDataSetChanged()
        }
    }
```

startActivityResult도 필요 없어집니다. 단순 startActivity로 바꿉니다.

```kotlin
//    val startForResult =
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//            if (it.resultCode == Activity.RESULT_OK) {
////                refreshAdapter()
//            }
//        }
```
