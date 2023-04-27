# Kotlin의 Broadcast Receiver와 Alarm Manager

이 문서에서는 Kotlin에서 사용되는 Broadcast Receiver와 Alarm Manager에 대해 설명합니다.

## Broadcast Receiver

### 개요

Broadcast Receiver는 안드로이드 시스템에서 발생하는 여러 이벤트(예 : SMS 도착, 배터리 부족 등)를 수신하여 처리할 수 있도록 해주는 컴포넌트입니다.

### BroadcastReceiver 등록하기

```kotlin
val filter = IntentFilter()
filter.addAction("android.intent.action.BATTERY_LOW")

val receiver = object : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // do something when battery is low
    }
}

registerReceiver(receiver, filter)
```

## Alarm Manager

### 개요

Alarm Manager는 안드로이드 시스템에서 지정한 시간에 애플리케이션을 실행하거나 브로드캐스트 메시지를 보내도록 예약할 수 있도록 해주는 클래스입니다.

### Alarm 등록하기

```
val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
val intent = Intent(this, MyBroadcastReceiver::class.java)
val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)

val triggerTime = System.currentTimeMillis() + 60 * 1000 // 1 minute later
alarmManager.set(AlarmManager.RTC_WAKEUP, triggerTime, pendingIntent)
```
