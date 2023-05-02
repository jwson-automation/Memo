# 실습 2

LiveData LifeCycle

--> Timer Data를 만들겁니다.

## 코드 분석

1. Zeneric<> 속의 데이터를 Observe합니다.

```kotlin
class TimerLiveData : LiveData<Long>()
```

2. onActive(), onInActive()

onActive : 활성 상태의 관찰자가 있을 때,
onInActive : 활성 상태의 관찰자가 없을 때

쉽게 말해서 옵저브 되는 데이터의 화면(액티비티)가 화면 있을 때, Active상태입니다.

--> 따라서, 라이프 사이클을 인식하고 따라간다고 표현합니다!

3. Timer

initialTime이 변수로 정해져 있어서 껐다가 켜도 다시 시간이 여전히 된 것 처럼 보입니다.

## 코드

```kotlin
class TimerLiveData : LiveData<Long>() {

    private val initialTime: Long = SystemClock.elapsedRealtime()
    private var timer: Timer? = null

    override fun onActive() {
        Log.d(TAG, "onActive: ")
        timer = Timer()
//         scheduleAtFixedRate(task, start, peroid) : start시간부터 period 간격으로 task를  수행
        timer?.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val newValue = (SystemClock.elapsedRealtime() - initialTime) / 1000
                postValue(newValue)
            }
        }, ONE_SECOND, ONE_SECOND)
    }

    override fun onInactive() {
        Log.d(TAG, "onInactive: ")
        timer?.cancel()
    }

    companion object {
        private const val ONE_SECOND = 1000.toLong()
    }
}
```
