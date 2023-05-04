# Data Binding

# 실습

```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:orientation="horizontal">

        <TextView
            android:id="@+id/text1"
            android:text="Hello"
            android:textSize="24sp"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"/>

</LinearLayout>
```

1. 먼저 레이아웃으로 감싸줍니다.
2. DTO객체를 가져오고, 사용해줍니다.

```xml
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">

    <data>

        <variable
            name="notes"
            type="com.ssafy.jetpackall.database.NotesDto" />
    </data>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="horizontal">

        <TextView
            android:id="@+id/text1"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:text="@{notes.TITLE}"
            android:textSize="24sp" />

    </LinearLayout>
</layout>
```

3. 어댑터에서 데이터를 주입을 해주려고 합니다.
4. binding이 없어서 곤란합니다.

```kotlin
class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
    var listData: List<NotesDto> = emptyList()

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnCreateContextMenuListener {
        //context Menu 등록.필수.
        init {
            itemView.setOnCreateContextMenuListener(this)
        }

        fun bind(noteDto: NotesDto) {
            itemView.findViewById<TextView>(R.id.text1).text = noteDto.TITLE
        }

        //context Menu 생성
        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            val menuItem = menu?.add(0, DELETE_ID, 0, "Delete Memo");
            //context menu event 처리
            menuItem?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem?): Boolean {
                    this@NoteListActivity.deleteNote(listData[layoutPosition].ID)
                    return true
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
//            val binding = NotesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false )
//            val binding = DataBindingUtil.inflate<NotesRowBinding>(LayoutInflater.from(parent.context),R.layout.notes_row,parent,false)
//            val view = binding.root
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notes_row, parent, false)
        return NoteViewHolder(view).apply {
            //목록 선택 event 처리  --> 수정으로 보냄.
            itemView.setOnClickListener {
                val intent = Intent(parent.context, NoteEditActivity::class.java)
                intent.putExtra(KEY_ROWID, listData[layoutPosition].ID)
//                    this@NoteListActivity.startForResult.launch(intent)
                startActivity(intent)
            }
        }
    }
```

5. binding으로 바꿔줍니다. view가 아닌

```
val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notes_row, parent, false)
```

```
//            val binding = NotesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false )
```

```kotlin

    inner class NoteListAdapter : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {
        var listData: List<NotesDto> = emptyList()

        inner class NoteViewHolder(val binding: NotesRowBinding) :
            RecyclerView.ViewHolder(binding.root),
            View.OnCreateContextMenuListener {
            //context Menu 등록.필수.
            init {
                itemView.setOnCreateContextMenuListener(this)
            }

            fun bind(noteDto: NotesDto) {
                binding.notes = noteDto
            }

            //context Menu 생성
            override fun onCreateContextMenu(
                menu: ContextMenu?,
                v: View?,
                menuInfo: ContextMenu.ContextMenuInfo?
            ) {
                val menuItem = menu?.add(0, DELETE_ID, 0, "Delete Memo");
                //context menu event 처리
                menuItem?.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem?): Boolean {
                        this@NoteListActivity.deleteNote(listData[layoutPosition].ID)
                        return true
                    }
                })
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
            val binding =
                NotesRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
//            val binding = DataBindingUtil.inflate<NotesRowBinding>(LayoutInflater.from(parent.context),R.layout.notes_row,parent,false)
//            val view = binding.root
//        val view =
//            LayoutInflater.from(parent.context).inflate(R.layout.notes_row, parent, false)
            return NoteViewHolder(binding).apply {
                //목록 선택 event 처리  --> 수정으로 보냄.
                itemView.setOnClickListener {
                    val intent = Intent(parent.context, NoteEditActivity::class.java)
                    intent.putExtra(KEY_ROWID, listData[layoutPosition].ID)
//                    this@NoteListActivity.startForResult.launch(intent)
                    startActivity(intent)
                }
            }
        }

        override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
            val noteDto = listData[position]
            holder.bind(noteDto)
        }

        override fun getItemCount(): Int {
            return listData.size
        }
    }
```
