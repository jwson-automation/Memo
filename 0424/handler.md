# Android UI Thread

일반 Thread는 Looper가 없고, 메인 Thread만 기본적으로 Looper를 가지고 있다.

그래서 Thread간의 통신을 위해서는 루퍼를 만들어야 하고,

만드는 방법은 prepare the Looper를 만들거나 Handler로 루퍼를 만들어야 한다.

아래 처럼 Handler를 만들어서 textView를 바꾸면 이미지를 새로 호출 할 떄, Main Thread를 사용해서 수정을 하기 떄문에 오류가 발생하지 않는다.

## 코드

```kotlin

        isRunning = true
        val thread = ThreadClass()
        thread.start()


    inner class ThreadClass : Thread() {
        override fun run() {
            val handler = Handler(Looper.getMainLooper())
            while(isRunning) {
                sleep(100)
                handler.post{
                    binding.helloTextview.text = System.currentTimeMillis().toString()
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()

        isRunning = true
    }
    override fun onStop() {
        super.onStop()

        isRunning = false
    }

```
