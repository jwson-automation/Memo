# 시험 준비

## 추가 문제

### Activity Life Cycle

onCreate : 액티비티 생성 시
onStart : STARTED 상태 진입 -> 보여짐 ->
onResume : RESUME 상태 유지 -> 액티비티로 돌아오면 다시 RESUME 발생
onPause : 잠시 액티비티를 떠났을 때, 호출되는 메소드
onStop : 화면이 꺼지면 Stop이 호출된다.
onDestroy : 사용자가 앱을 종료하거나, 기기회전등으로 일시적으로 액티비티를 소멸시키면 발생한다.

## 안드로이드 구조
앱을 실행하면 아랫방향으로 내려가서 필요한 기능들을 수행, 정보를 가져온다.
```
앱 
: 내가 만든 앱
---
JAVA API Framework
: Content Provider, View System, Activity, Location, ..
---
NativeC/C++
: WebKit, Media Framework

Android Runtime
: Android Runtime(ART), Core Libraries
---
HardWare Abstraction Layer(HAL)
: Audio, Bluetooth, Camera, Sensors
---
Linux Kernel
: Audio, Keypad, Binder, Camera, WIFI ....
```
### Android Manifst.xml은 정보가 많습니다.
```
<manifest>: 이 태그는 전체 매니페스트 파일을 감싸는 루트 요소입니다. package 속성을 사용하여 앱의 고유한 식별자를 지정하며, 이것은 앱이 플레이 스토어에 업로드 될 때 사용됩니다. 버전 정보도 이 태그 안에 포함됩니다.

<application>: 이 태그는 앱의 모든 구성 요소를 포함하는 데 사용됩니다. 여기에는 액티비티, 서비스, 브로드캐스트 리시버 및 콘텐츠 프로바이더와 같은 앱의 모든 구성 요소에 대한 선언이 포함됩니다. 또한 앱 아이콘, 라벨 및 테마와 같은 앱의 시각적 측면을 정의하는 속성도 포함됩니다.

<activity>: 이 태그는 앱의 개별 액티비티를 정의합니다. 각 액티비티는 사용자 인터페이스 화면이며, 다른 액티비티와 독립적으로 존재할 수 있습니다. 인텐트 필터를 사용하여 특정 액티비티가 어떤 작업을 수행할 수 있는지 지정할 수 있습니다.

<service>: 이 태그는 앱의 백그라운드에서 실행되는 서비스를 정의합니다. 서비스는 오랫동안 실행되거나 사용자 인터페이스와 상호 작용하지 않는 작업을 수행하는 데 사용됩니다.

<receiver>: 이 태그는 브로드캐스트 리시버를 정의합니다. 브로드캐스트 리시버는 시스템 또는 다른 앱에서 전송되는 브로드캐스트 메시지를 수신하는 컴포넌트입니다.

<provider>: 이 태그는 앱의 콘텐츠 프로바이더를 정의합니다. 콘텐츠 프로바이더는 앱 간 데이터 공유를 가능하게 합니다.

<uses-permission>: 이 태그는 앱이 필요로 하는 권한을 선언합니다. 예를 들어 카메라 액세스 또는 인터넷 액세스와 같은
```

### 4대 컴포넌트는 서로 아무 관계가 없습니다.
```
컴포넌트는 앱의 구성단위로, 여러개를 조합하여 하나의 앱을 만듭니다.

연결되어 있지 않으며 독립적이고, Main 시작점이 없습니다. 

따라서 Entering Point는 팝업으로 들어오냐, 앱을 실행시켜서 들어오냐에 따라서 변화될 수 있습니다. ( Launch : SpecificActivity )
```

### Intent
Intent를 이용해서 4대 컴포넌트 사이를 이동 가능합니다.
```
// 다음 화면으로 이동하는 코드입니다.
val intent = Intent(this, NextActivity::class.java)
startActivity(intent)
```

### Intent 값 반환
```
// ActivityResultLauncher 객체 생성
val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
    if (result.resultCode == RESULT_OK) {
        val data: Intent? = result.data
        val resultValue = data?.getStringExtra("result")
        // 결과값 처리
    }
}

// 결과값을 반환받는 코드
val intent = Intent(this, NextActivity::class.java)
resultLauncher.launch(intent)
```

### Broad Case Reciver

```
정적 등록은 AndroidManifest.xml 파일에서 리시버를 등록하는 방법입니다. 이 방법은 앱이 설치될 때 시스템에 등록되므로, 앱이 실행되지 않더라도 브로드캐스트 메시지를 수신할 수 있습니다. 이 방법의 장점은 앱이 실행되지 않아도 브로드캐스트를 수신할 수 있다는 것입니다. 단점으로는 리시버가 등록된 상태로 유지되기 때문에 앱이 더 이상 필요하지 않은 경우에도 시스템에서 제거되지 않는다는 것입니다.

동적 등록은 앱이 실행되는 도중에 리시버를 등록하는 방법입니다. 이 방법은 앱이 실행되어야만 리시버가 등록되며, 앱이 종료되면 리시버도 함께 제거됩니다. 이 방법의 장점은 필요한 경우에만 리시버를 등록할 수 있다는 것입니다. 단점으로는 앱이 종료되면 리시버도 함께 제거되기 때문에, 앱이 실행되지 않을 때 브로드캐스트를 수신할 수 없다는 것입니다.

따라서, 정적 등록은 앱이 실행되지 않아도 브로드캐스트를 수신할 수 있지만, 시스템에서 제거되지 않는 단점이 있고, 동적 등록은 앱이 실행될 때만 리시버를 등록할 수 있지만, 앱이 종료되면 제거되기 때문에 앱의 자원을 절약할 수 있다는 장점이 있습니다.
```

### Broad Case Receiver 사용자 정의
" 

### Inten

### Resource 폴더의 용도들

### Asset 폴더의 용도들

### Open


## Kotlin 문법

### Constructor (중요)

### 주생성자 vs 부생성자

### Lateinit Lazy

### CLASS ( DATA, OBJECT, ... )

### Syntax

### Java하고 다른 부분..

### Menu들 ( 중요 )

### 알람매니저 권장사항 ( 중요 )

##