# ContentProvider

하나의 안드로이드에 다수의 CP가 존재합니다.

그렇다면, 내 앱과 CP를 어떻게 통신 시켜야 할까요?

또 어떻게 동기화를 유지해야 할까요?

그게 바로 `val resolver = contentResolver` 입니다.

1. 결과를 가져오는 `Corsor`를 생성합니다.
2. uri를 잘 넣어줍니다.
3. Query 도 잘 넣어줍니다.

## ContentResolver

### 실습 1
```kotlin
class SimpleResolverActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_resolver)

        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 200)
        } else {
            init()
        }
    }

    private fun init() {
        var textview = findViewById<TextView>(R.id.textview)
        // var uri = "content://com.android.contacts/contacts"
//        var uri = ContactsContract.Contacts.CONTENT_URI.toString()+"/1"
        // uri를 텍스트로 만들고,
//        var uri2 = Uri.parse(uri)
        // 타입을 Uri로 바꿔줬습니다.

        val URI = ContactsContract.Contacts.CONTENT_URI

        var cursor = contentResolver.query(URI, null,null,null,null)
        cursor.use {
            if (it != null){
                if (it.moveToFirst()){
                    for (i in 0 .. it.columnCount - 1){
                        textview.append("${i},${it.getColumnName(i)}, ${it.getString(i)} \n")
                        Log.d(TAG, "init: ${it.getColumnName(i)}, ${it.getString(i)}")
                    }

                }
            }
        }
        // use는 다쓰고 닫아주는 친구입니다.


            


    }
}
```

### 실습 2 ( Custom Cursor)

```Kotlin
class SimpleResolverActivity2 : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_resolver2)

        if (checkSelfPermission(Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 200)
        } else {
            init()
        }
    }

    val URI = ContactsContract.Contacts.CONTENT_URI // 전체 주소록
    private fun init(){

        val listview = findViewById<ListView>(R.id.listview)
//        val URI = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, "1".toLong()) //1 번만 조회

        val cursor = contentResolver.query(URI, null, null, null, null)
        val from = arrayOf( "_id", "display_name")
        val to = intArrayOf( R.id.id_item, R.id.name_item)

        val adapter1 = SimpleCursorAdapter(
            this,
            R.layout.list_item,
            cursor!!,
            from,
            to,
            FLAG_REGISTER_CONTENT_OBSERVER
        )
        listview.adapter = adapter1
    }

    //상속해서 구현하면 주소록 변경시 onContentChanged 콜백됨.
    inner class MySimpleCursorAdapter(
        context: Context, layout:Int, cursor:Cursor, from:Array<String>, to:IntArray, flag:Int
    ) : SimpleCursorAdapter(context, layout, cursor,from, to, flag){
        override fun onContentChanged() {
            super.onContentChanged()
            Log.d(TAG, "onContentChanged: ")
//            cursor.requery()

            val newCursor = contentResolver.query(URI, null, null, null, null)
            swapCursor(newCursor)
        }
    }

}
```

다음과 같이 수정해준다.

```kotlin
val adapter1 = MySimpleCursorAdapter(
            this,
            R.layout.list_item,
            cursor!!,
            from,
            to,
            FLAG_REGISTER_CONTENT_OBSERVER
        )
```

여기에서 OBSERVER는 실시간으로 계속 데이터를 바인딩된 상태로 유지시켜준다!