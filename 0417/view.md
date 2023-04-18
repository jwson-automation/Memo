# View

만들어진 웹 페이지를 직접 보여주는 것

화면을 다이나믹하게 만들 수는 있지만, 네이티브 만큼의 성능을 보여주기는 힘들다.

## WebView

아래와 같이 넣어줬더니, 그냥 크롬으로 연결되어 버립니다.

```kotlin
binding.webView.loadUrl("https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp")
```

설정을 아직 안해줬기 때문입니다!

```kotlin
binding.webView.webViewClient = WebViewClient()
binding.webView.loadUrl("https://www.ssafy.com/ksp/jsp/swp/swpMain.jsp")
```

하지만, 네이버는 열리는데 SSAFY는 안열립니다.

JavaScript로 동작을 하냐 안하냐의 차이가 있습니다.

`SSAFY는 처음에 js기반의 DOM으로 가져오기 때문에, JavaScript를 켜줘야 화면 로딩이 이루어 지는 것`

단순링크(get방식) vs 자바스크립트(js를 이용한 버튼)

그래서 네이버에서도 자바스크립트를 켜주지 않으면 특정 버튼이 활성화 되지 않습니다.

## VideoView

파일 형태의 비디오만 재생이 가능합니다. (Uri를 통한 직접적인 Youtube 링크 재생은 불가능)

```kotlin
        val VIDEO_URL = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        val uri = Uri.parse(VIDEO_URL)
```

```kotlin
        val VIDEO_PATH = "android.resource://" + packageName + "/" + R.raw.sample
        val uri = Uri.parse(VIDEO_PATH)
```

```kotlin
        // VideoView 가 보여줄 동영상의 경로 주소(Uri) 설정
        binding.videoView.setVideoURI(uri)
        // Video 재생, 일시정지 등을 할 수 있는 컨트롤바 부착
        binding.videoView.setMediaController(MediaController(this))
        // Video 로딩 준비가 끝났을 때 동영상을 실행하도록 리스너 설정
        binding.videoView.setOnPreparedListener {
            binding.videoView.start()
        }
```

## CalendarView

달력 보여주기

- 아무것도 하지 않아도 오늘 날짜가 선택되어 집니다.

```kotlin
// 캘린더 인스턴스 가져오기
        val calendar = Calendar.getInstance()

        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            // 캘린더 인스턴스에 CalendarView 에서 선택한 날짜 세팅
            calendar.set(year, month, dayOfMonth)

            // 날짜 표기법 Format
            val dateFormatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
            val formattedDate = dateFormatter.format(calendar.time)

            // TextView 에 날짜 세팅하기
            binding.dateTv.text = formattedDate
        }

        val today = Date()
        val dateFull = DateFormat.getDateInstance(DateFormat.FULL)
        val dateLong = DateFormat.getDateInstance(DateFormat.LONG)
        val dateMedium = DateFormat.getDateInstance(DateFormat.MEDIUM)
        val dateShort = DateFormat.getDateInstance(DateFormat.SHORT)

        Log.d(TAG, "FULL : ${dateFull.format(today)}")
        Log.d(TAG, "LONG : ${dateLong.format(today)}")
        Log.d(TAG, "MEDIUM : ${dateMedium.format(today)}")
        Log.d(TAG, "SHORT : ${dateShort.format(today)}")
```

## ProgressBar

```Kotlin
binding.progressStartBtn.setOnClickListener {
            i = binding.progressBar.progress

            Thread {
                while (i < 100) {
                    // 진행 단계 100까지 +1 반복
                    i += 1
                    // Progress Bar 의 진행상황을 TextView 를 통해 보여주기
                    handler.post {
                        binding.progressBar.progress = i
                        binding.progressStateTv.text = i.toString() + "/" + binding.progressBar.max
                    }
                    // Progress 진행상황을 천천히 보여주기
                    Thread.sleep(100)
                }
            }.start()
        }
```

## SeakBar

```Kotlin
binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.progressTv.text = "onProgressChanged : $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                binding.progressTv.text = "onStartTrackingTouch : ${seekBar!!.progress}"
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                binding.progressTv.text = "onStopTrackingTouch : ${seekBar!!.progress}"
            }

        })
```

## RatingBar

```Kotlin
binding.ratingBarDefault.onRatingBarChangeListener =
            RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
                binding.ratingBarMiddle.rating = rating
                binding.ratingBarSmall.rating = rating
            }
```

## SearchView

검색하는 창, 유행에 따라가지만 최근에는 많이 사용하는지 모르겠음.

툴바에 넣는 것도 가능!

```Kotlin
binding.searchViewAlone.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                binding.searchViewAloneTv.text = query

                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
```

## TextureView

textrue : `질감`
3D 그래픽에서 말하는 texture를 뜻합니다.

`Content Stream` : 완전히 다운로드되지 않고 재생되는 오디오 또는 비디오 파일입니다 .
Content Stream을 Display에 표현할 때 사용하는 위젯 입니다.

```Kotlin
val VIDEO_PATH = "android.resource://" + packageName + "/" + R.raw.sample
        val uri = Uri.parse(VIDEO_PATH)

        binding.textureView.surfaceTextureListener = object : TextureView.SurfaceTextureListener {

            // Texture Size 가 변경되면 호출되는 메서드입니다.
            override fun onSurfaceTextureSizeChanged(surface: SurfaceTexture, width: Int, height: Int) {

            }

            // Texture 가 파괴되면 호출되는 메서드입니다.
            override fun onSurfaceTextureDestroyed(surface: SurfaceTexture): Boolean {

                return false
            }

            // Texture 업데이트가 발생하면 호출되는 메서드입니다.
            override fun onSurfaceTextureUpdated(surface: SurfaceTexture) {

            }

            // Texture 가 준비되면 호출되는 메서드입니다. 초기화 처리를 해줍니다.
            override fun onSurfaceTextureAvailable(surfaceTexture: SurfaceTexture, width: Int, height: Int) {
                // MediaPlayer 를 만들어줍니다.
                mPlayer = MediaPlayer()

                // 인자로 받은 surfaceTexture 를 기반으로 Surface 를 생성해줍니다.
                val surface = Surface(surfaceTexture)

                // 생성한 Surface 를 MediaPlayer 에 세팅해줍니다.
                mPlayer.setSurface(surface)

                mPlayer.setDataSource(this@TextureViewActivity, uri)

                // MediaPlayer 를 준비시켜줍니다.
                // 관련 메소드로는 prepare()와 prepareAsync()가 있습니다.
                // prepare()는 동기 처리, prepareAsync()는 비동기 처리에 사용됩니다.
                // 로컬 파일의 경우 prepare()로 동기처리를 해도 되지만,
                // 스트리밍 기능을 개발하는 경우에는 prepareAsync()로 비동기 처리를 해야합니다.

                mPlayer.prepare()

                mPlayer.setOnPreparedListener { mp ->
                    // 준비가 완료되면, 비디오를 재생합니다.
                    mp.start()
                }
            }
```

화면에 예쁘게 자동으로 맞춰주는 기술은 없습니다.

잘 설정해줘야 합니다.

화면의 x,y를 가져옵니다.

```Kotlin
val display = windowManager.currentWindowMetrics
val displayX = display.bounds.width()
val displayY = display.bounds.height()
```

영상의 x,y를 가져옵니다.

```Kotlin
val videoX = mPlayer.videoWidth
val videoY = mPlayer.videoHeight
```

이제 비율을 조절해주면 됩니다. ( 수학공식짜기 )

```kotlin
val resultX = videoX * (displayX/videoX)
val resultY = videoY * (displayX/videoY)
```

Video View와 다르게 비디오 컨트롤 바를 꺼내는 것은 어렵다. ( 가능은 하다! )
