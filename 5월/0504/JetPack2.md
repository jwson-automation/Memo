# ViewModel 적용

## 코드

1. View Model을 생성합니다.

- NoteViewModel
- NoteListViewModel

2. NoteListViewModel을 아래와 같이 생성합니다.

```kotlin

// 목록 조회, 단건 조회, 삭제

// 액티비티에서 할지, 뷰모델에서 할지는 취향의 영역

class NoteListViewModel : ViewModel() {
    val repo: NoteRepository = NoteRepository.get()

    // 목록 조회
    val noteList: LiveData<MutableList<NotesDto>> = repo.getNotes()

    // 단건 조회
    fun getNote(id: Long): LiveData<NotesDto> = repo.getNote(id)

    // 삭제
    fun deleteNote(dto: NotesDto) {
        viewModelScope.launch {
            repo.deleteNote(dto)
        }
    }
```

3. Activity에서 viewModel을 가져와줍니다.

```kotlin
class NoteListActivity : AppCompatActivity() {

    private val noteListViewModel:NoteListViewModel by viewModels()
```

- GetNote를 viewModel 그대로 가져와주기

`getNote()` > `noteListViewModel.noteList`

```kotlin
 private fun initAdapter() {
        listAdapter = NoteListAdapter()
        noteListViewModel.noteList.observe(this) {
            listAdapter.listData = it
            listAdapter.notifyDataSetChanged()
        }
```

- 딜리트 viewModel로 사용하기

```kotlin
private fun deleteNote(id: Long) {
        noteListViewModel.getNote(id).observe(this) {
            if (it != null) {
                noteListViewModel.deleteNote(it)
            }
        }
    }
```

4. NoteViewModel 생성
   !!뷰 모델에서 기존의 activity에 있던 `mRowid`를 관리해주겠다는 것!

- 이전에 비정상 종료에 대한 로직을 짰었는데.
- 전부 뷰 모델로 넘겨줄겁니다.

먼저 `SavedStateHandle`로 비정상 종료를 대비해줍니다.

```kotlin
class NoteViewModel(private val handle: SavedStateHandle) : ViewModel() {
    var _id = handle.get<Long>("_id") ?: 0
        set(value) {
            handle.set("-id", value)
            field = value
        }
    fun setId(id : Long){
        _id = id
    }
}
```

5. NoteActivity 수정

```kotlin
        noteRepository = NoteRepository.get()

// 뷰모델 사용 x
//        mRowId = savedInstanceState?.getLong(KEY_ROWID) ?: -1L
//
//        // -1 이면 앞 Activity에서 넘어온 경우이므로, intent에서 값을 꺼내서, 있으면 수정. 입력:-1
//        if (mRowId == -1L) {
//            val extras = intent.extras
//            mRowId = extras?.getLong(KEY_ROWID) ?: -1L
//        }

// 뷰모델 사용
        val id = intent.extras?.getLong(KEY_ROWID) ?: -1L
        vm.setId(id)
```

6. Repository도 viewModel에 넣어줍니다.

```kotlin

// SavedStateViewModel
// 비정상 종료 되어도 handle은 유지된다.
class NoteViewModel(private val handle: SavedStateHandle) : ViewModel() {
    var _id = handle.get<Long>("_id") ?: 0
        private set(value) {
            handle.set("-id", value)
            field = value
        }

    fun setId(id: Long) {
        _id = id
    }

    // 단건 조회, 입력, 수정
    val note: LiveData<NotesDto> = NoteRepository.get().getNote(_id)

    fun insert(dto: NotesDto) {
        viewModelScope.launch {
            NoteRepository.get().insertNote(dto)
        }
    }
    fun update(dto:NotesDto){
        viewModelScope.launch {
            NoteRepository.get().updateNote(dto)
        }
    }
}
```

7. 기존의 액티비티의 repo를 뷰모델로 바꿔줍니다.

```kotlin
class NoteEditActivity : AppCompatActivity() {

    private lateinit var mTitleText: EditText
    private lateinit var mBodyText: EditText
    private val vm: NoteViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)

        val id = intent.extras?.getLong(KEY_ROWID) ?: -1L
        vm.setId(id)


        findViewById<Button>(R.id.confirm).setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun initView() {
        mTitleText = findViewById<View>(R.id.title) as EditText
        mBodyText = findViewById<View>(R.id.body) as EditText

        if (vm._id != -1L) {
            var note = NotesDto()
            vm.note.observe(this) {
                mTitleText.setText(note.TITLE)
                mBodyText.setText(note.BODY)
            }

        }
    }

    //비정상 종료시는 id를 기록한다.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ROWID, vm._id)
    }

    //pause에서 저장하도록 구현
    override fun onPause() {
        super.onPause()
        saveState()
    }

    override fun onResume() {
        super.onResume()
        initView()
    }

    //-1은 앞 Activity에서 입력(insert)으로 넘어온 경우
    private fun saveState() {
        val title = mTitleText.text.toString()
        val body = mBodyText.text.toString()
        if (vm._id == -1L) {
            CoroutineScope(Dispatchers.IO).launch {
                vm.insert(NotesDto(title, body))
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                vm.update(NotesDto(vm._id, title, body))
            }
        }
    }
}
```
