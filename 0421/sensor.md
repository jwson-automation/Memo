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


