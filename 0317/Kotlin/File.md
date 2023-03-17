# File

## 개요

자바 기반으로 파일에 입출력합니다.
`Node Stream`
`Processing Stream`

```
자바에서는 bufferedWriter, new, bufferedReader... 
코틀린에서는 ?
```

### saveFile
파일을 저장합니다.
```kotlin
file = File(filesDir, "data.txt")

  private fun saveFile(){
        try {
            // use: try use resource
            BufferedWriter(FileWriter(file, true)).use {
                it.append("지금 시각은 ${Date()} 입니다.\n")
            }
            statusTV.text = "저장 완료"
        }catch(e:IOException){
            Log.e(TAG, "saveFile: ", e)
        }
    }
```

### loadFile

파일을 읽어옵니다.
```kotlin
private fun loadFile(){
        Log.d(TAG, "file: ${file.canonicalPath}")
        try {
            //useLines : BufferedReader로 한줄씩 읽어서 처리. 모두 읽으면 close한다.
            BufferedReader(FileReader(file)).useLines {
                //fold : 초기값을 설정하고 요소의 첫번째 부터 람다식 오른쪽의 누적값으로 적용하여 accu가 되고, 다음값으로..
                var data =it.fold(""){
                    accu, now ->
                    "$accu\n$now"
                }
                Log.d(TAG, "loadFile: $data")
                statusTV.text = data
            }

        }catch(e:IOException){
            Log.e(TAG, "loadFile: ", e)
        }
    }
```

### loadAssets

`assets.open()`

assets 폴더 안의 특정 파일을 읽어 올 때 사용합니다.
```kotlin
private fun loadAssets(){
        try{
            BufferedReader(InputStreamReader(assets.open("data.txt"))).useLines {
                var data =it.fold(""){
                        accu, now ->"$accu\n$now"
                }
                Log.d(TAG, "loadFile: $data")
                statusTV.text = data
            }
        }catch(e:IOException){
            Log.e(TAG, "onCreate: assets 파일 로딩 실패", e)
        }
    }
```


### 외부 디렉토리

`getExternalFilesDir()`외장 메모리 경로를 리턴합니다.
`val file` 로 저장하고 오픈합니다.

```kotlin
externalBtn.setOnClickListener {
            Log.d(TAG, "현재 미디어의 상태 ${Environment.getExternalStorageState()}")
            Log.d(TAG, "외장 메모리 경로: ${getExternalFilesDir(null)}") // null: 시스템 default 경로 리턴.
            if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
                val file = File(getExternalFilesDir(null), "data.txt")
                try{
                    //FileWriter(file, true) 이면 기존파일에 추가.
                    BufferedWriter(FileWriter(file)).use {
                        it.append("외부 저장소에 write 하기")
                    }
                    BufferedReader(FileReader(file)).useLines {
                        //fold : 초기값을 설정하고 요소의 첫번째 부터 람다식 오른쪽의 누적값으로 적용하여 accu가 되고, 다음값으로..
                        val lines = it.fold(""){
                                accu, now ->"$accu\n$now"
                        }
                        statusTV.text = lines
                    }
                }catch(e: IOException){
                    Log.e(TAG, "onCreate: 외장 메모리 사용 실패", e)
                }
            }else{
                Toast.makeText(this, "이 앱은 외장 메모리를 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
```


### 전체 코드

``` Kotlin
class MyFileOutActivity : AppCompatActivity() {
    private lateinit var statusTV: TextView
    private lateinit var saveBtn:Button
    private lateinit var loadBtn:Button
    private lateinit var file: File

    private lateinit var assetsBtn:Button
    private lateinit var externalBtn:Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_out)

        statusTV = findViewById(R.id.status_tv)
        saveBtn = findViewById(R.id.save_btn)
        loadBtn = findViewById(R.id.load_btn)
        file = File(filesDir, "data.txt")
        assetsBtn = findViewById(R.id.assets_btn)
        externalBtn = findViewById(R.id.external_btn)

        saveBtn.setOnClickListener {
            saveFile()
        }
        loadBtn.setOnClickListener {
            loadFile()
        }
        assetsBtn.setOnClickListener {
            loadAssets()
        }

        externalBtn.setOnClickListener {
            Log.d(TAG, "현재 미디어의 상태 ${Environment.getExternalStorageState()}")
            Log.d(TAG, "외장 메모리 경로: ${getExternalFilesDir(null)}") // null: 시스템 default 경로 리턴.
            if(Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED){
                val file = File(getExternalFilesDir(null), "data.txt")
                try{
                    //FileWriter(file, true) 이면 기존파일에 추가.
                    BufferedWriter(FileWriter(file)).use {
                        it.append("외부 저장소에 write 하기")
                    }
                    BufferedReader(FileReader(file)).useLines {
                        //fold : 초기값을 설정하고 요소의 첫번째 부터 람다식 오른쪽의 누적값으로 적용하여 accu가 되고, 다음값으로..
                        val lines = it.fold(""){
                                accu, now ->"$accu\n$now"
                        }
                        statusTV.text = lines
                    }
                }catch(e: IOException){
                    Log.e(TAG, "onCreate: 외장 메모리 사용 실패", e)
                }
            }else{
                Toast.makeText(this, "이 앱은 외장 메모리를 지원하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

  private fun saveFile(){
        try {
            // use: try use resource
            BufferedWriter(FileWriter(file, true)).use {
                it.append("지금 시각은 ${Date()} 입니다.\n")
            }
            statusTV.text = "저장 완료"
        }catch(e:IOException){
            Log.e(TAG, "saveFile: ", e)
        }
    }

    private fun loadFile(){
        Log.d(TAG, "file: ${file.canonicalPath}")
        try {
            //useLines : BufferedReader로 한줄씩 읽어서 처리. 모두 읽으면 close한다.
            BufferedReader(FileReader(file)).useLines {
                //fold : 초기값을 설정하고 요소의 첫번째 부터 람다식 오른쪽의 누적값으로 적용하여 accu가 되고, 다음값으로..
                var data =it.fold(""){
                    accu, now ->
                    "$accu\n$now"
                }
                Log.d(TAG, "loadFile: $data")
                statusTV.text = data
            }

        }catch(e:IOException){
            Log.e(TAG, "loadFile: ", e)
        }
    }
    private fun loadAssets(){
        try{
            BufferedReader(InputStreamReader(assets.open("data.txt"))).useLines {
                var data =it.fold(""){
                        accu, now ->"$accu\n$now"
                }
                Log.d(TAG, "loadFile: $data")
                statusTV.text = data
            }
        }catch(e:IOException){
            Log.e(TAG, "onCreate: assets 파일 로딩 실패", e)
        }
    }
}
```