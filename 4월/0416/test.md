1. 액티비티의 라이프사이클

```
onCreate()
onStart() <- onRestart()
onResume()
onPause()
onStop() -> onRestart()
onDestory()
```

2. Intent의 Action

```
ACTION_MAIN
ACTION_VIEW
ACTION_DEFAULT
...
```

3. Intent의 Data
: 수행할 데이터의 URI

4. Permission

```
Install time permissions

Runtime Permissions
실행시 획득해야 하는 권한 ( 코드로 구현해야함 )

Special Permissions

퍼미션 체크 : checkSelfPermissions
퍼미션 요청 : requestPermissions

퍼미션 결과 : onRequestPermissionResult

```

5. Launch Mode / Intent의 Flag


```

standar : 기본 모드
singletop : 런치모드 ="singleTop" or intent.addFlags(FLAG_ACTIVITY_SINGLE_TOP)
singleTask : 중복을 허용하지 않으며 앞에 잇는 activity
singelInstance : 모든 task를 대상으로 중복된 activity를 허용x (좀 이상함)

Flag_activity_new_task + flag_activity_clear_task 함께 사용함

clear top

flag activity no history
```

6. 서비스의 라이프사이클

```
[start Service]
onCreate()
onStartCommand()
onDestroy()

[bind Service]
onCreate()
onBind()
onUnbind()
onDestroy()
```

7. bind Service 3가지 방법

```
- Local(Service <-> Activity)
- notLocal(Service <- differ -> Activity)
- AIDL(AIDL을 생성, 서비스 구현)
```

8. StartService > ForeGround Service

```
- 퍼미션 android.permission.FOREGROUND_SERVICE
액티비티 : apllicationContext.startForegroundService(intent)
서비스 : startForeground(ONGOING_NOTIFICATION_ID, notification)
```

9. Notification

```
만들기 나름
PendingIntent
builder (channel을 먼저 만들어야함)
setSmallIcon
setContextTitle ...
```

10. Fragment 라이프사이클

```
onCreate
onCreateView
onViewCreate
onViewStateRestored
onStart
onResume
onPause
onStop
onSaveInstanceState
onDestroyView
onDestroy
```

11. Frgemnt Manager
supportFragmentManager
parentFragmentManager
childFragmentManager

12. Fragment 통신
```
1. interface
2. Bundle ( fragmentActivity.changeFragemntView(Param)) 파라미터 넣어주기
Acitivty에서 하위 Fragment를 생성하면서 전달 
arguments?.getString("value") 알규먼츠라는 걸 사용해서 읽음
3. FragmentResult (편리함)
[jetPack 의존성 추가]
보낼때 -> binding.nameEt.setText("하이")
받을때 -> setFragmentResult()
```

13. CP, CR

```
contentProvider < - > contentResolver
값을 기억해서 넣어주면 됐습니다.
```

14. XML 파서

```
SAX와 DOM은 모두 XML 파싱에 사용되는 방식입니다.

SAX는 Simple API for XML의 약자로, XML을 한 줄씩 읽어서 이벤트를 발생시키면서 처리하는 방식입니다. SAX는 XML 문서가 커지면 커질수록 더욱 효율적이며, 메모리를 적게 사용합니다. SAX는 이벤트 단위로 처리하기 때문에, XML 문서를 순차적으로 처리하며, 특정 태그나 속성을 찾을 때 효율적입니다.

DOM은 Document Object Model의 약자로, XML 문서를 전체를 메모리에 로딩하여 객체화하는 방식입니다. DOM은 XML 문서를 트리 구조로 만들어서 각 노드를 객체로 표현합니다. DOM은 XML 문서를 수정하거나 탐색할 때 효율적이지만, 메모리를 많이 사용합니다. 따라서, XML 문서가 크면 큰 문제를 일으킬 수 있습니다.

간단히 말해, SAX는 한 줄씩 읽어서 처리하며, DOM은 전체 문서를 객체로 변환하여 처리하는 방식입니다. SAX는 속도가 빠르고, DOM은 수정과 탐색이 효율적입니다.
```

```
// XML 파일을 읽어들이기 위한 InputStream을 생성합니다.
val inputStream: InputStream = context.assets.open("sample.xml")

// XML 파서를 생성합니다.
val xmlPullParser: XmlPullParser = Xml.newPullParser()

// XML 파서에 InputStream을 전달합니다.
xmlPullParser.setInput(inputStream, null)

// 파싱을 시작합니다.
var eventType = xmlPullParser.eventType
while (eventType != XmlPullParser.END_DOCUMENT) {
    when (eventType) {
        XmlPullParser.START_TAG -> {
            // 시작 태그를 만났을 때 처리할 작업을 작성합니다.
            val tagName = xmlPullParser.name
            Log.d(TAG, "Start tag: $tagName")
        }
        XmlPullParser.END_TAG -> {
            // 끝 태그를 만났을 때 처리할 작업을 작성합니다.
            val tagName = xmlPullParser.name
            Log.d(TAG, "End tag: $tagName")
        }
        XmlPullParser.TEXT -> {
            // 태그 사이의 텍스트를 만났을 때 처리할 작업을 작성합니다.
            val text = xmlPullParser.text
            Log.d(TAG, "Text: $text")
        }
    }
    eventType = xmlPullParser.next()
}

// InputStream을 닫습니다.
inputStream.close()
```
