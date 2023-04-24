# AsyncTask

Level30에서 deprecated되었음

`Asynchronous` 비동기를 Thread, Handler, Message, Runable 없이 사용 가능!!

`doInBackground()` 를 이용해서 쓰레드에서 비동기 작업을 실행합니다.

나머지는 ForeGround에서 동작합니다.

아래 코드를 보면 `binding.textView.text = values[0].toString()`를

onProgressUpdate에서 실행시키는데, 저 부분에서만 Main Thread 동작을하기 때문입니다.

```kotlin
@Suppress("DEPRECATION")
// deprrecation 무시
class AsyncTaskActivity : AppCompatActivity() {
    lateinit var binding : ActivityAsyncTaskBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAsyncTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val asyncTask = MyAsyncTask(binding.textView)
        asyncTask.execute()

    }

    inner class MyAsyncTask(textView: TextView) : AsyncTask<Void, Int, Boolean>() {

        override fun doInBackground(vararg params: Void?): Boolean {
            for(i in 0..500) {
                SystemClock.sleep(100)
                publishProgress(i)
            }
            return true
        }

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun onPostExecute(result: Boolean?) {
            super.onPostExecute(result)
        }

        override fun onProgressUpdate(vararg values: Int?) {
            super.onProgressUpdate(*values)
            //vararg : 가변인자, "*"을 붙이면 가변인자로 취급
            binding.textView.text = values[0].toString()
        }

        override fun onCancelled(boolean: Boolean) {
            super.onCancelled()
        }

    }
}
```
