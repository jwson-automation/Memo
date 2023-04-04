# 0404

ToolBar, 다양한 알림

## Toolbar vs Actionbar vs Appbar

- 버전에 따라서 차이가 있습니다.
- 롤리팝까지(Action bar)

### 실습 1. 툴바 생성

1. 일단 기존의 툴 바를 제거합니다. `theme.xml` > `NoActionBar`
2. toolbar를 findViewById로 가져옵니다. `물론 xml에서 수정해도 됩니다.`
3. 메뉴를 추가하고 싶다면, 메뉴를 추가하고 연결합니다. `inflateMenu(R.menu.-)`
4. 메뉴 선택 후 이벤트를 추가합니다. `toolbar.setOnMenuItemClickListener()`

```
[주의!] `onCreateOptonsMenu`와 다릅니다! `onOptionsItemSelected`로 수정할 수 없습니다.
```

### Sample Code

```Kotlin
//toolbar customizing
        toolbar = findViewById(R.id.toolbar)
        // 이렇게 나타내는게 Basic 이고
//        toolbar.title = "hello,world!"
//        toolbar.background = resources.getDrawable(R.color.teal_200, theme)

        // 이렇게 apply로도 나타낼 수 있습니다.
        toolbar.apply {
            title = "hey"
            background = resources.getDrawable(R.color.teal_200, theme)
            inflateMenu(R.menu.menu_main)
        }

        toolbar.setOnMenuItemClickListener(){
            when(it.itemId) {
                R.id.action_search -> Log.d(TAG, "onCreate: search")
                R.id.action_setting -> Log.d(TAG, "onCreate: setting")
                else -> Log.d(TAG, "onCreate: else")
            }
            true
        }
```

### 실습 2. 스크롤과 따라 움직이는 툴바 생성

1. xml 파일이 툴바와 함께, 화면에 다 나오지 않는 커다란 NestedScrollView를 만듭니다.
2. `layout_behavior` 옵션을 이용하면 스크롤과 함께 툴바가 움직이게 해줍니다.
3. `layout_scrollFlag` 옵션을 이용하면, 천장에 붙은 툴바 혹은 내리면 내려오는 툴바로 설정이 가능하다. `설명하는게 재밌다`

### 실습 3. Collapsing 툴바 ( 접히는 툴바 )

1. 기존의 툴바를 CollapsingToolBarLayout에 담는다.
2. AppBarLayout에 넣는다.
3. 펼쳐졌을 때와 접혀졌을때를 구분해서 만들어 주면 됩니다.
4. 애니메이션도 넣어주면 됩니다.

```
 `layout_collapseMode`를 이용해서 메뉴 아이템도 지워줄 수 있습니다.
 `layout_collapseParallaxMultiplier`를 이용해서 변화 속도를 줄 수 있습니다.
```

## Custom View

화면에 보이는 것은 모두 View의 자식, 서브클래스 입니다.
직접 뷰를 만들어 보겠습니다. ( 게임 같은거 만들 때 필요합니다. )

- View에서 상속받아와서 생성합니다.
- 모든 화면의 View는 항상 Context가 있습니다. ( 어디에 붙어 있는지 알아야함 )
- AttributeSet을 매개변수로 하는 생성자를 구성합니다. ( 바꾸고 싶은 것들을 Attribute로 세팅한다.)

### 실습 4. Custom View

1. Layout을 가져옵니다.
2. 생성자에 Context와 AttributeSet을 넣어줍니다.
3. init을 해줍니다. (findViewById...)
4. 데이터 타입은 `attrs.xml`에 선언해줍니다.
5. getAttrs를 해줍니다....

### 샘플코드

```Kotlin
class CustomNameCard : ConstraintLayout {
    lateinit var iv: ImageView
    lateinit var name: TextView
    lateinit var org: TextView

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
        getAttrs(attrs)
    }

    private fun init() {
        val view = LayoutInflater.from(context).inflate(R.layout.custom_name_card_view, this, false)
        addView(view);
        iv = findViewById(R.id.user_img_iv)
        name = findViewById(R.id.userName)
        org = findViewById(R.id.user_org_tv)
    }
    // attrs.xml 파일로부터 속성 정보 확보 - typedArray
    private fun getAttrs(attrs: AttributeSet) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomNameCard)
        setTypedArray(typedArray)
    }
    // 속성값을 view 요소들에 연결
    private fun setTypedArray(typedArray: TypedArray) {
        name.text = typedArray.getText(R.styleable.CustomNameCard_userName)
        org.text = typedArray.getText(R.styleable.CustomNameCard_userOrg)
        iv.setImageResource(
            typedArray.getResourceId(
                R.styleable.CustomNameCard_userImg,
                R.drawable.ic_launcher_foreground
            )
        )
        typedArray.recycle()
    }
}
```

### 실습 5. 그림판 만들기

```
package com.ssafy.userinterface_4.paint_basic

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

data class Point(var x:Float, var y:Float, var isContinue:Boolean)

private const val TAG = "DrawSample_싸피"
class DrawSample : View  {
    var list = arrayListOf<Point>()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) { }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paint = Paint()
        paint.color = Color.BLUE
        paint.strokeWidth = 10f

        list.forEachIndexed { index, point ->
            if(point.isContinue){
                canvas.drawLine(list[index-1].x,list[index-1].y,point.x, point.y, paint)
//                canvas.drawPoint(point.x,point.y,paint)
            }
        }

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                list.add(Point(event.x,event.y,false))
                Log.d(TAG, "onTouchEvent: DOWN")
            }
            MotionEvent.ACTION_MOVE -> {
                list.add(Point(event.x,event.y,true))
                Log.d(TAG, "onTouchEvent: MOVE")
            }
            MotionEvent.ACTION_UP -> {
                list.add(Point(event.x,event.y,false))
                Log.d(TAG, "onTouchEvent: UP")
            }
        }
        invalidate()

        //touch이후에 event를 전달할것인가? true: 여기서 종료. false :뒤로 전달.
        // touch -> click -> longclick
        return true
    }

}
```

### 실습 6. 진동 주기

1. `manifest.xml`에서 `permission.VIBRATE`를 켜줍니다.
2. 코드를 잘 써줍니다.
3. 매너모드를 무시하고 울리는 것도 가능합니다.

### 샘플코드

```Kotlin
val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) { // API Level 31에서 VibratorManager로 변경됨
            val vibratorManager = this.getSystemService(Context.VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator;
        } else {
            getSystemService(VIBRATOR_SERVICE) as Vibrator
        }

// 1. 진동 발생(일회성)
        btn1.setOnClickListener {

            Log.d(TAG, "onCreate: ${vibrator}")
            // deprecated  Build.VERSION_CODES.O
            if(Build.VERSION.SDK_INT >= 26 ) {
                vibrator.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }else{
                vibrator.vibrate(500);
            }
        }

        // 2. 패턴 진동 발생(일회성)
        btn2.setOnClickListener {
            val vibratorTiming = longArrayOf(100, 400, 100, 100, 100, 100)
            val effect = VibrationEffect.createWaveform(vibratorTiming, -1)
            vibrator.vibrate(effect)
        }

        // 3. 패턴 진동 반복
        btn3.setOnClickListener {
            val vibratorTiming = longArrayOf(100, 400, 100, 100, 100, 100)
            val effect = VibrationEffect.createWaveform(vibratorTiming, 2)
            vibrator.vibrate(effect)
        }

//        // 4. 패턴 진동 취소
        btn4.setOnClickListener {
            vibrator.cancel()
        }

        // 5. 패턴 진동 세기 조절 (일회성)
        btn5.setOnClickListener {
            val vibratorTiming = longArrayOf(100, 400, 100, 100, 100, 100)
            val vibratorAmplitudes = intArrayOf(0, 50, 0, 200,0, 200)
            val effect = VibrationEffect.createWaveform(vibratorTiming, vibratorAmplitudes,-1)
            vibrator.vibrate(effect)
        }

```

### 실습 7. 소리 출력

1. 구현하면 됩니다.

### 샘플 코드

```Kotlin
val uriRingtone = Uri.parse("android.resource://" + packageName + "/" + R.raw.jazzbyrima)
        val ringtone = RingtoneManager.getRingtone(this, uriRingtone)

        // 6.Ringtone
        btn6.setOnClickListener {
            if(btn6.text == "Ringtone_start"){
                ringtone.play()
                btn6.setText("Ringtone_stop")
            }else{
                ringtone.stop()
                btn6.setText("Ringtone_start")
            }
        }

        val uriRingtone2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        //val uriRingtone2 = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
        val ringtone2 = RingtoneManager.getRingtone(this, uriRingtone2)

        // 7.default_bellsound_start
        btn7.setOnClickListener {
            if(btn7.text == "default_bellsound_start"){
                ringtone2.play()
                btn7.setText("default_bellsound_stop")
            }else{
                ringtone2.stop()
                btn7.setText("default_bellsound_start")
            }
        }
    }
```

### 실습 8. 다이얼로그

뒤는 그림자지고 '정말로 나가시겠습니까?` 창 뜨는게 다이얼로그입니다.

`여기서 확인 버튼이 왼쪽인지 오른쪽 인지는 디자인적, 철학적인 이야기가 가능..`

`다이얼로그 버튼, 날짜 선택 형식, 키보드 자판... 안드로이드와 맥OS의 차이에 대해서도 제대로 알고 있어야 한다. `

### 예제코드

```Kotlin
class DialogActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog)
        val btn1 = findViewById<Button>(R.id.btn1)
        val btn2 = findViewById<Button>(R.id.btn2)
        val btn3 = findViewById<Button>(R.id.btn3)
        val btn4 = findViewById<Button>(R.id.btn4)
        val btn5 = findViewById<Button>(R.id.btn5)
        val tv1 = findViewById<TextView>(R.id.tv1)

        // 1. 기본 다이얼로그
        btn1.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("기본 다이얼로그")
            builder.setMessage("기본 다이얼로그")
            builder.setIcon(R.mipmap.ic_launcher)

            // 버튼 클릭시에 무슨 작업을 할 것인가!
            val listener = DialogInterface.OnClickListener { p0, p1 ->
                when (p1) {
                    DialogInterface.BUTTON_POSITIVE ->
                        tv1.text = "BUTTON_POSITIVE"
                    DialogInterface.BUTTON_NEUTRAL ->
                        tv1.text = "BUTTON_NEUTRAL"
                    DialogInterface.BUTTON_NEGATIVE ->
                        tv1.text = "BUTTON_NEGATIVE"
                }
            }
            builder.setPositiveButton("Positive", listener)
            builder.setNegativeButton("Negative", listener)
            builder.setNeutralButton("Neutral", listener)
            builder.show()
        }

        // 2. 커스텀 다이얼로그
        btn2.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("커스텀 다이얼로그")
            builder.setIcon(R.mipmap.ic_launcher)

            val v1 = layoutInflater.inflate(R.layout.dialog, null)
            builder.setView(v1)
            // p0에 해당 AlertDialog가 들어온다. findViewById를 통해 view를 가져와서 사용
            val listener = DialogInterface.OnClickListener { p0, p1 ->
                val alert = p0 as AlertDialog
                val edit1: EditText = alert.findViewById(R.id.editText)
                val edit2: EditText = alert.findViewById(R.id.editText2)

                tv1.text = "${edit1.text}"
                tv1.append("${edit2.text}")
            }
            builder.setPositiveButton("확인", listener)
            builder.setNegativeButton("취소", null)
            builder.show()
        }

        // 3. 날짜 다이얼로그
        btn3.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val listener = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                // i년 i2월 i3일
                tv1.text = "${year}년 ${month + 1}월 ${day}일"
            }

            val picker = DatePickerDialog(this, listener, year, month, day)
            picker.show()
        }


        // 4. 시간 다이얼로그
        btn4.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR)
            val minute = calendar.get(Calendar.MINUTE)

            val listener = TimePickerDialog.OnTimeSetListener { timePicker, hour, min ->
                tv1.text = "${hour}시 ${min}분"
            }

            val picker = TimePickerDialog(this, listener, hour, minute, false)
            // true하면 24시간제
            picker.show()
        }
    }
}
```

### 예제코드2

```Kotlin
class DialogActivity2 : AppCompatActivity() {
    private val DIALOG_YES_NO_MESSAGE = 1
    private val DIALOG_YES_NO_LONG_MESSAGE = 2
    private val DIALOG_LIST = 3
    private val DIALOG_PROGRESS = 4
    private val DIALOG_SINGLE_CHOICE = 5
    private val DIALOG_MULTIPLE_CHOICE = 6
    private val DIALOG_TEXT_ENTRY = 7

    private val MAX_PROGRESS = 100

    private lateinit var mProgressDialog: ProgressDialog
    private var mProgress = 0
    private lateinit var mProgressHandler: Handler

    private fun createDialog(id: Int): Dialog? {
        when (id) {
            DIALOG_YES_NO_MESSAGE -> return AlertDialog.Builder(this@DialogActivity2)
                .setIcon(R.drawable.ic_outline_warning_24)
                .setTitle(R.string.alert_dialog_two_buttons_title)
                .setPositiveButton(R.string.alert_dialog_ok,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked OK so do some stuff */ })
                .setNegativeButton(R.string.alert_dialog_cancel,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked Cancel so do some stuff */ })
                .create()
            DIALOG_YES_NO_LONG_MESSAGE -> return AlertDialog.Builder(this@DialogActivity2)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.alert_dialog_two_buttons_msg)
                .setMessage(R.string.alert_dialog_two_buttons2_msg)
                .setPositiveButton(R.string.alert_dialog_ok,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked OK so do some stuff */ })
                .setNeutralButton(R.string.alert_dialog_something,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked Something so do some stuff */ })
                .setNegativeButton(R.string.alert_dialog_cancel,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked Cancel so do some stuff */ })
                .create()
            DIALOG_LIST -> return AlertDialog.Builder(this@DialogActivity2)
                .setTitle(R.string.select_dialog)
                .setItems(R.array.select_dialog_items,
                    DialogInterface.OnClickListener { dialog, which -> /* User clicked so do some stuff */
                        val items = resources.getStringArray(R.array.select_dialog_items)
                        AlertDialog.Builder(this@DialogActivity2)
                            .setMessage("You selected: " + which + " , " + items[which])
                            .show()
                    })
                .create()
            DIALOG_PROGRESS -> {
                //ProgressBar를 사용하는것으로 권장하고 ProgressDialog는 deprecated
                mProgressDialog = ProgressDialog(this@DialogActivity2)
                mProgressDialog.setIcon(R.drawable.alert_dialog_icon)
                mProgressDialog.setTitle(R.string.select_dialog)
                mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                mProgressDialog.max = MAX_PROGRESS
                mProgressDialog.setButton(
                    getText(R.string.alert_dialog_hide)
                ) { dialog, whichButton -> /* User clicked Yes so do some stuff */ }
                mProgressDialog.setButton2(
                    getText(R.string.alert_dialog_cancel)
                ) { dialog, whichButton -> /* User clicked No so do some stuff */ }
                return mProgressDialog
            }
            DIALOG_SINGLE_CHOICE -> return AlertDialog.Builder(this@DialogActivity2)
                .setIcon(R.drawable.alert_dialog_icon)
                .setTitle(R.string.alert_dialog_single_choice)
                .setSingleChoiceItems(R.array.select_dialog_items2, 0,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked on a radio button do some stuff */ })
                .setPositiveButton(R.string.alert_dialog_ok,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked Yes so do some stuff */ })
                .setNegativeButton(R.string.alert_dialog_cancel,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked No so do some stuff */ })
                .create()
            DIALOG_MULTIPLE_CHOICE -> return AlertDialog.Builder(this@DialogActivity2)
                .setIcon(R.drawable.ic_popup_reminder)
                .setTitle(R.string.alert_dialog_multi_choice)
                .setMultiChoiceItems(R.array.select_dialog_items3,
                    booleanArrayOf(false, true, false, true, false, false, false),
                    OnMultiChoiceClickListener { dialog, whichButton, isChecked -> /* User clicked on a check box do some stuff */ })
                .setPositiveButton(R.string.alert_dialog_ok,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked Yes so do some stuff */ })
                .setNegativeButton(R.string.alert_dialog_cancel,
                    DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked No so do some stuff */ })
                .create()
            DIALOG_TEXT_ENTRY -> {
                // This example shows how to add a custom layout to an AlertDialog
                val factory = LayoutInflater.from(this)
                val textEntryView: View = factory.inflate(R.layout.alert_dialog_text_entry, null)
                return AlertDialog.Builder(this@DialogActivity2)
                    .setIcon(R.drawable.alert_dialog_icon)
                    .setTitle(R.string.alert_dialog_text_entry)
                    .setView(textEntryView)
                    .setPositiveButton(R.string.alert_dialog_ok,
                        DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked OK so do some stuff */ })
                    .setNegativeButton(R.string.alert_dialog_cancel,
                        DialogInterface.OnClickListener { dialog, whichButton -> /* User clicked cancel so do some stuff */ })
                    .create()
            }
        }
        return null
    }

    /**
     * Initialization of the Activity after it is first created.  Must at least
     * call [android.app.Activity.setContentView] to
     * describe what is to be displayed in the screen.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dialog2)

        /* Display a text message with yes/no buttons and handle each message as well as the cancel action */
        val twoButtonsTitle = findViewById<View>(R.id.two_buttons) as Button
        twoButtonsTitle.setOnClickListener { createDialog(DIALOG_YES_NO_MESSAGE)!!.show() }

        /* Display a long text message with yes/no buttons and handle each message as well as the cancel action */
        val twoButtons2Title = findViewById<View>(R.id.two_buttons2) as Button
        twoButtons2Title.setOnClickListener { createDialog(DIALOG_YES_NO_LONG_MESSAGE)!!.show() }


        /* Display a list of items */
        val selectButton = findViewById<View>(R.id.select_button) as Button
        selectButton.setOnClickListener { createDialog(DIALOG_LIST)!!.show() }

        /* Display a custom progress bar */
        val progressButton = findViewById<View>(R.id.progress_button) as Button
        progressButton.setOnClickListener {
            createDialog(DIALOG_PROGRESS)!!.show()
            mProgress = 0
            mProgressDialog.progress = 0
            mProgressHandler.sendEmptyMessage(0)
        }

        /* Display a radio button group */
        val radioButton = findViewById<View>(R.id.radio_button) as Button
        radioButton.setOnClickListener { createDialog(DIALOG_SINGLE_CHOICE)!!.show() }

        /* Display a list of checkboxes */
        val checkBox = findViewById<View>(R.id.checkbox_button) as Button
        checkBox.setOnClickListener { createDialog(DIALOG_MULTIPLE_CHOICE)!!.show() }

        /* Display a text entry dialog */
        val textEntry = findViewById<View>(R.id.text_entry_button) as Button
        textEntry.setOnClickListener { createDialog(DIALOG_TEXT_ENTRY)!!.show() }
        mProgressHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                super.handleMessage(msg)
                if (mProgress >= MAX_PROGRESS) {
                    mProgressDialog.dismiss()
                } else {
                    mProgress++
                    mProgressDialog.incrementProgressBy(1)
                    mProgressHandler.sendEmptyMessageDelayed(0, 100)
                }
            }
        }
    }

}

```

### 실습 9. progress dialog

진행상태 바를 보여주려고 할 때,

1. `for 반복문 + sleep` 을 넣어서 `mProgress`값을 강제로 변화시켜도 프로그레스바는 동작하지 않습니다.
2. `Thread`간의 통신을 하기 위해서는 `Handler`라는 것을 가지고 있습니다.
3. 따라서 `mProgressHandler`를 사용해야만 가능합니다.

```Kotlin
Thread{
                for (i in 1..100){
//                    mProgress = i 이거 안되니까 핸들러 쓸거임
                    mProgressHandler.sendEmptyMessage(0)
//                    Log.d("hey", "onCreate: $i")
//                    Thread.sleep(10)
                }
            }.start()
        }
```
