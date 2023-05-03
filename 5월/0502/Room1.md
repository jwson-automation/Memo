# 실습 1

1. Entity 작성
2. DAO 작성
3. RoomDatabase 작성
4. Repository 작성
5. Repository와 DAO 연결
6. DB 호출 부분 Coroutine으로 변경

## 코드 작성

1. Entity 작성 (DTO)

```kotlin
@Entity(tableName = "note")
data class NotesDto(val TITLE:String= "title", val BODY:String="body") {

    @PrimaryKey(autoGenerate = true)
    var ID: Long = 0

    constructor(id:Long, title:String, content:String): this(title, content){
        this.ID = id;
    }
}
```

2. DAO 작성

Select문은 쿼리를 넣어줘야 합니다.
Inert는 중복 처리를 해줘야 합니다.

```kotlin
@Dao
interface NoteDao {

    @Query("Select * FROM note")
    fun getNotes(): MutableList<NotesDto>

    @Query("Select * FROM note WHERE ID = (:id)")
    fun getNote(id: Long): NotesDto

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(dto: NotesDto)

    @Update
    fun updateNote(dto: NotesDto)

    @Delete
    fun deleteNote(dto: NotesDto)
}
```

3. Database 작성

```kotlin
@Database(entities = [NotesDto::class], version = 1)
abstract class NoteDatabase :RoomDatabase(){
    abstract fun noteDao() : NoteDao
}
```

4. Repository 작성

\_1. Room.databaseBuilder로 만들거고, 여기에 아까 만든 Database를 넣습니다. (`databaseBuilder`)

\_2. build()를 해줍니다.

\_3. database.noteDao를 가져와줍니다.

\_4. noteDao의 메소드를 사용 가능하도록 repository에서 method로 다시 만들어줍니다.

\_5. application에서 처음에 이니셜라이즈 해주고

\_6. companion object로 싱글톤 처리 해줍니다.

```kotlin
class NoteRepository private constructor(context: Context){

    private val database : NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val noteDao = database.noteDao()

    fun getNotes() : MutableList<NotesDto> {
        return noteDao.getNotes()
    }

    fun getNote(id : Long) : NotesDto {
        return noteDao.getNote(id)
    }
    fun insertNote(dto: NotesDto){
        noteDao.insertNote(dto)
    }
    fun updateNote(dto: NotesDto){
        noteDao.updateNote(dto)
    }
    fun deleteNote(dto : NotesDto) {
        noteDao.deleteNote(dto)
    }

    companion object{
        private var INSTANCE : NoteRepository? =null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = NoteRepository(context)
            }
        }

        fun get() : NoteRepository {
            return INSTANCE ?:
            throw IllegalStateException("NoteRepository must be initialized")
        }
    }

}
```

```kotlin
class NoteApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()
        NoteRepository.initialize(this)
    }
}
```

5. DAO와 Repository의 연결

아래 그대로 하면 오류가 납니다!

이유는 Main Thread에서 Repository를 쓰려고 했기 때문입니다!

Repository는 DB통신을 위한 도구로 당연히 서비스적인 영역을 담당하려고 합니다. Main에서 실행시키지 마십시오!

그래서 메소드에 `suspend`를 달아주면 안에서 async await를 알아서 해줍니다, 바로 비동기 호출를 해주는겁니다!

(추가 : suspend를 달아줬으니, liveData도 연결 가능하다. 그건 내일 모레 할 예정)

CUD는 `withTransaction`으로 묶어줘야, 에러 발생 시의 오염을 막을 수 있다.

아래 디펜던시를 추가해서 Dispatcher 자동연결되는 lifecycleScope로 해줄 수 있다.

```
// LifeCycle Scope
    implementation 'androidx.lifecycle:lifecycle-runtime-ktx:2.6.1'
```

`suspend`처리를 해준뒤에 코루틴을 이용한 비동기 처리를 해준다.

```kotlin
private fun initAdapter() {
        listAdapter = NoteListAdapter()
        lifecycleScope.launch {
            listAdapter.listData = getData()
            listAdapter.notifyDataSetChanged()
        }
```

```kotlin
private const val TAG = "NoteRepository_싸피"
private const val DATABASE_NAME = "note-database.db"
class NoteRepository private constructor(context: Context){

    private val database : NoteDatabase = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        DATABASE_NAME
    ).build()

    private val noteDao = database.noteDao()

    suspend fun getNotes() : MutableList<NotesDto> {
        return noteDao.getNotes()
    }

    suspend fun getNote(id : Long) : NotesDto {
        return noteDao.getNote(id)
    }
    suspend fun insertNote(dto: NotesDto) = database.withTransaction{
        noteDao.insertNote(dto)
    }
    suspend fun updateNote(dto: NotesDto) = database.withTransaction{
        noteDao.updateNote(dto)
    }
    suspend fun deleteNote(dto : NotesDto) = database.withTransaction {
        noteDao.deleteNote(dto)
    }

    companion object{
        private var INSTANCE : NoteRepository? =null

        fun initialize(context: Context){
            if(INSTANCE == null){
                INSTANCE = NoteRepository(context)
            }
        }

        fun get() : NoteRepository {
            return INSTANCE ?:
            throw IllegalStateException("NoteRepository must be initialized")
        }
    }

}



```

### Room 적용 전 코드

1. DTO

```kotlin
data class NotesDto(val TITLE:String= "title", val BODY:String="body") {

    var _ID : Long = -1

    constructor(id:Long, title:String, content:String): this(title, content){
        _ID = id;
    }
}
```

2. DAO

```kotlin
 fun insertNote(title: String?, body: String?): Long {
        val initialValues = ContentValues()
        initialValues.put(KEY_TITLE, title)
        initialValues.put(KEY_BODY, body)
        return mDb.insert(DATABASE_TABLE, null, initialValues)
    }

    fun updateNote(rowId: Long, title: String?, body: String?): Boolean {
        val args = ContentValues()
        args.put(KEY_TITLE, title)
        args.put(KEY_BODY, body)
        return mDb.update(DATABASE_TABLE, args, "$KEY_ROWID=$rowId", null) > 0
    }

    fun deleteNote(rowId: Long): Boolean {
        return mDb.delete(DATABASE_TABLE, "$KEY_ROWID=$rowId", null) > 0
    }

    fun selectAllNotes(): MutableList<NotesDto>  {
        val list = mutableListOf<NotesDto>()

        mDb.query(
            DATABASE_TABLE, arrayOf(
                KEY_ROWID, KEY_TITLE,
                KEY_BODY
            ), null, null, null, null, null
        ).use{
            if(it.moveToFirst()) {
                do {
                    list.add(NotesDto(it.getLong(0), it.getString(1), it.getString(2)))
                } while (it.moveToNext())
            }
        }
        return list
    }

    fun selectNote(rowId: Long): NotesDto {
        mDb.query(
            true, DATABASE_TABLE, arrayOf(
                KEY_ROWID,
                KEY_TITLE, KEY_BODY
            ), "$KEY_ROWID=$rowId", null,
            null, null, null, null
        ).use {
            it.moveToFirst()
            return NotesDto(it.getLong(0), it.getString(1), it.getString(2))
        }
    }
```
