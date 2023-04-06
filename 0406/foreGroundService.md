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


## 
