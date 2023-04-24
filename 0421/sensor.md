# 센서

```
1. 환경 센서
2. 동작 센서
3. 위치 센서
```

```
1. SensorManager
2. Sensor
3. SensorEvent
4. SensorEventListener
```

## 센서 출력 예제코드

```Kotlin
val sm = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val sensors = sm.getSensorList(Sensor.TYPE_ALL)

        var sensorlist="전체 내장 센서의 수 : "+sensors.size+"\n"
        for (sensor in sensors) {
            sensorlist +=
                "name: ${sensor.name}\n" +
                "power: ${sensor.power}\n"+
                "resolution: ${sensor.resolution}\n"+
                "range: ${sensor.maximumRange}\n\n"
        }

        val textView = findViewById<TextView>(R.id.textView)
        textView.movementMethod = ScrollingMovementMethod()
        textView.text = sensorlist

```


## SensorEvent

int accuracy : 정확도
Sensor sensor : 발생한 센서
long timestamp : 이벤트가 발생한 시각
float[] values : 센서 데이터를 담고 있는 배열


## 실습 (내려가는 하트 만들기)
```kotlin
package com.ssafy.app_sensor

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import com.ssafy.app_sensor.databinding.ActivityBasicBinding

//화면에  하트 배치...
private const val TAG = "BasicActivity_싸피"

class BasicActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBasicBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBasicBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initTouchListener()

    }

    @SuppressLint("ClickableViewAccessibility")
    fun initTouchListener() {
        binding.root.setOnTouchListener { view, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                creatImageView(motionEvent.x, motionEvent.y)
                Log.d(TAG, "initTouchListener: touched!")
            }

            true
        }
    }

    fun creatImageView(eventX: Float, eventY: Float) {
        val imageView = ImageView(this).apply {
            setBackgroundResource(R.drawable.ic_baseline_favorite_24)
            layoutParams = LinearLayout.LayoutParams(SIZE, SIZE)
            x = eventX
            y = eventY
        }

        binding.root.addView(imageView)
        list.add(imageView) // 만든 이미지 뷰를 담아주기
    }

    override fun onResume() {
        super.onResume()
        isRunning = true
        startThread()
    }

    override fun onPause() {
        super.onPause()
        isRunning = false
    }

    var isRunning = false

    fun startThread() {
        Thread {
            while (isRunning) {
                Thread.sleep(1_000)
                handler.post {
                    list.map {
                        it.y += 10
                        // 여기에서 y값을 따로 화면에 불러오지 않아도, View.setY라는 메소드를 눌러보면
                        // 자동적으로 해주고 있다.
                    }
//                list.forEach{
//                    it.x = it.x + 10
//                }
                }
            }
        }.start()

    }

    val handler = Handler(Looper.getMainLooper())

    val list = arrayListOf<ImageView>()

    companion object {
        val SIZE = 150
    }
}


```

