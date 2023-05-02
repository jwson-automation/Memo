# 실습 1

`MutableLiveData` : 수정 가능한 라이브데이터

`value` : ui Thread
`postValue` : background handler

## 코드

```kotlin
override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val text = findViewById<TextView>(R.id.text)
        //백그라운드 스레드에서 호출
        liveString.postValue("postValue Write")

        //반드시 메인 스레드에서만 호출
        liveString.value = "setValue Write"

        liveString.observe(this){ liveString ->
            Log.d(TAG, "value : $liveString")
            text.text = liveString
        }
```

```
postValue() : 이 메소드는 LiveData 값을 업데이트하는 데 사용됩니다. 그러나 postValue()는 백그라운드 스레드에서 호출되어야 합니다. 이것은 LiveData를 관찰하는 UI 컴포넌트가 메인 스레드에서 실행되도록 하기 위한 것입니다. postValue()를 사용하면 백그라운드 스레드에서 안전하게 LiveData 값을 업데이트할 수 있습니다. 업데이트된 값은 UI 스레드에서 관찰할 수 있습니다.

value : 이 속성은 LiveData 값을 업데이트하는 데 사용됩니다. 그러나 이 속성은 메인 스레드에서 호출되어야 합니다. 만약 백그라운드 스레드에서 이 속성을 호출하면 MainThreadAccessException 예외가 발생합니다. value를 사용하면 메인 스레드에서 LiveData 값을 업데이트할 수 있습니다. 업데이트된 값은 UI 스레드에서 관찰할 수 있습니다.

value가 postValue()보다 더 빠른 이유는 postValue()가 백그라운드 스레드에서 호출되고 메인 스레드에서 실행되기 때문입니다. postValue()는 백그라운드 스레드에서 호출하여 값을 업데이트하고, 이후에 메인 스레드에서 UI를 업데이트하는 데 시간이 걸립니다.

반면에 value는 메인 스레드에서 직접 호출되므로 postValue()에 비해 더 빠릅니다. value는 메인 스레드에서 즉시 값을 업데이트하고 UI를 업데이트할 수 있습니다.
```
