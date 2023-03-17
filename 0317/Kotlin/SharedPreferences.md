# SharedPreferences

`preference`는 앱을 종료해도 남아있습니다.

앱 외부에 파일로 데이터를 전달해서 보관해주는 창고를 만드는 기술입니다.

그냥 getSharedPreferences하면 `getSharedPreference.xml` 파일에 저장되고,
"text"를 넣어주면 `text.xml`에 저장됩니다.

안타깝게도 List 타입, 순서가 있는 타입은 넣어줄 수 없어서
JSon을 넣어주는 방식으로 복잡한 자료에도 활용합니다.

```kotlin
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class PreferenceActivity : AppCompatActivity() {
    private lateinit var prefResultEv :TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_preference)
        prefResultEv = findViewById(R.id.pref_result)

```

아래처럼 prefs를 만들어주고 그냥 올려주면 됩니다.

```
        val prefs = getSharedPreferences("test", MODE_PRIVATE)
        // 정보 추출하기
        val v = prefs.getInt("KEY", 0)
        // 정보 저장하기
        val editor = prefs.edit()
        editor.putInt("KEY", v.toInt()+1)
        editor.commit()

        prefResultEv.text = "preference test: $v"
    }
}
```

아래 위치에 저장되면서 관리됩니다.
`2023-03-17 10 43 55.png`
