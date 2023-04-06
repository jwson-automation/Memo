# ForeGroundService

음악

오히려 쉽습니다.

## 일반적인 서비스

그냥 서비스는 아래와 같이

```Kotlin
class MyMusicService : Service() {

    private lateinit var mp: MediaPlayer

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        Log.d(TAG, "onCreate: ")
        super.onCreate()
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy: ")
        mp.stop() //음악 중지
        super.onDestroy()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand: ")
        mp = MediaPlayer.create(this, R.raw.jazzbyrima)
        mp.isLooping=true
        mp.start()
        return START_STICKY  // 시스템에 의해 종료되어도 다시 생성
    }
}
```

## 포어그라운드 서비스

음악이 꺼지지 않도록 하기 위해서는 아래와 같이 포어그라운드 서비스를 해야합니다.

핵심

```kotlin
private fun startForegroundService() {
        val notification = MusicNotification.createNotification(this)
        startForeground(NOTIFICATION_ID, notification)
    }
```

전체코드

```kotlin
class ForegroundMusicService : Service() {
    lateinit var mp: MediaPlayer

    override fun onCreate() {
        super.onCreate()
        mp = MediaPlayer.create(this,R.raw.jazzbyrima)
        Log.d(TAG, "onCreate()")
    }
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d( TAG, "Action Received = ${intent?.action}" )
        when (intent?.action) {
            Actions.START_FOREGROUND -> {
                Log.d(TAG, "Start Foreground 인텐트를 받음")
                if( !mp.isPlaying ){
                    mp.isLooping = true
                    mp.start()
                    startForegroundService()
                }
            }
            Actions.STOP_FOREGROUND -> {
                Log.d(TAG, "Stop Foreground 인텐트를 받음")
                if( mp.isPlaying ) {
                    stopForegroundService()
                    mp.stop() //음악 중지
                }
            }
            Actions.PLAY ->{
                Log.d(TAG, "start music from notification : ${mp.isPlaying}")
                if( !mp.isPlaying ){
                    Log.d(TAG, "start music from notification")
                    mp.start()
                }
            }
            Actions.STOP ->{
                Log.d(TAG, "stop music from notification")
                if( mp.isPlaying ) mp.pause() //음악 중지
            }
        }; return START_STICKY
    }

    private fun startForegroundService() {
        val notification = MusicNotification.createNotification(this)
        startForeground(NOTIFICATION_ID, notification)
    }

    private fun stopForegroundService() {
        stopForeground(true)
        stopSelf()
    }

    override fun onBind(intent: Intent?): IBinder? {
        // bindservice가 아니므로 null
        return null
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
    }

    companion object {
        const val NOTIFICATION_ID = 20
    }
}
```

## 채널

안드로이드 26버전 이후부터 알림 뿐만 아니라, 채널이라는 개념이 추가되었습니다.

필수알림, 마케팅알림, 진동, 소리, 무음, 등 푸쉬알림에 대한 구분들이 생겨났습니다.

## 예제코드

```Kotlin
object MusicNotification {
    const val CHANNEL_ID =
        "foreground_service_channel" // 임의의 채널 ID

    fun createNotification(context: Context): Notification {
        // 알림 클릭시 MainActivity로 이동됨
        val notificationIntent = Intent(context, MainActivity::class.java)
        notificationIntent.action = Actions.MAIN
        notificationIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(context,0,notificationIntent,FLAG_IMMUTABLE)

        // 각 버튼들에 관한Intent
        val prevIntent = Intent(context, ForegroundMusicService::class.java)
        prevIntent.action = Actions.PREV
        val prevPendingIntent = PendingIntent.getService(context, 0, prevIntent, FLAG_IMMUTABLE)

        val playIntent = Intent(context, ForegroundMusicService::class.java)
        playIntent.action = Actions.PLAY
        val playPendingIntent = PendingIntent.getService(context, 0, playIntent, FLAG_IMMUTABLE)

        val nextIntent = Intent(context, ForegroundMusicService::class.java)
        nextIntent.action = Actions.STOP
        val nextPendingIntent = PendingIntent.getService(context, 0, nextIntent, FLAG_IMMUTABLE)

        // 알림
        val notification =
            NotificationCompat.Builder(context, CHANNEL_ID).setContentTitle("Music Player")
                .setContentText("My Music").setSmallIcon(R.drawable.ic_baseline_album_24)
                .setOngoing(true) // true 일경우 알림 리스트에서 클릭하거나 좌우로 드래그해도 사라지지 않음
                .addAction(
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_previous,
                        "Prev",
                        prevPendingIntent
                    )
                ).addAction(
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_play,
                        "Play",
                        playPendingIntent
                    )
                ).addAction(
                    NotificationCompat.Action(
                        android.R.drawable.ic_media_next,
                        "STOP",
                        nextPendingIntent
                    )
                ).setContentIntent(pendingIntent)
                .build()

        // Oreo 부터는 Notification Channel을 만들어야 함
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "Music Player Channel", // 채널표시명
                NotificationManager.IMPORTANCE_DEFAULT
            )
            val manager = context.getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(serviceChannel)
        }; return notification
    }
}
```

```Kotlin
object Actions {
    private const val prefix = "com.ssafy.component3.action."
    const val MAIN = prefix + "main"
    const val PREV = prefix + "prev"
    const val NEXT = prefix + "next"
    const val PLAY = prefix + "play"
    const val START_FOREGROUND = prefix + "startforeground"
    const val STOP_FOREGROUND = prefix + "stopforeground"
    const val STOP = prefix +"stop"
}
```
