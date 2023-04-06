```
어제 복습

1. 액티비티, 인텐트

2. 암시적, 명시적 인텐트 ( 암시적이 어렵습니다. )

3. Task 관리 ( 런치모드 / 플래그 )

4. 실습 : 리사이클러뷰 + CRUD(오브젝트클래스) +
```

# Service

bindService - 어려운 친구
foregroundService - 예전에 알람때 해봤던 친구
Notification - 양식대로만 만들어 주면 되는 친구

목차
[bind Service](#bind-service)

## bind Service

아래처럼 서비스는 액티비티에게 데이터를 전달해줄 필요가 없다. 별개의 프로그램으로써 보이지 않는 영역에서 실행될 뿐이다.

하지만, 바인드 서비스는 액티비티와 Request, Response를 주고 받는다.

```
[service]
startService() > onCreate() > onStartCommand() > ServiceRunning > onDestroy()

[bindService]
bindService() > onCreate() > onBind() > BindRunning > onUnbind() > onDestroy
```

- 안드로이드 플랫폼 아키텍쳐의 가장 하단에
  `Linux Kernel`에 `Binder(IPC)`라는 시스템이 있는데, 그 부분과 관련이 있다.

### bindService 구현 방법

1. Local
   : `하나의 apk 안에 들어가 있는 것`

   `Q` : 액티비티 쓰지 서비스 왜 필요함?
   `A` : DB도 그냥 Activity에서 바로 Access하면 됩니다. 하지만,
   액티비티는 보여주기 위한 영역입니다.

   그러니까 DB는 백그라운드에 넣어주는게 더 구조적으로 분류가 더 잘되어 있는 것으로 보입니다.

   이렇게 하면 어제 실습에서 했던 것 처럼 object Class에서 DB를 만들어 두면 데이터가 여러 액티비티에서 자유롭게 처리할 수 있는 것 처럼

   아름다운 구조가 됩니다.

2. Local x
   : `apk가 다르다는 말입니다.`

   ```
   `Q` : 왜 apk 따로 분리함?
   `A` : 분리하는게 아니라, 다른 apk 사이에서의 통신을 도와주는 용도로 만드는 거임
   ```

   이게 특이한게 아니라, 안드로이드 플랫폼에서 다른 apk 사이의 통신은 꽤 많이 사용됩니다.

   앱 -> 지도
   문자 -> 뱅킹앱 , 등 ...

   - Messenger , Handler를 사용한다.
   - AIDL (Android Interface Definition Language) 를 이용하여 서비스 구현

   `AIDL` `인터페이스 정의언어란`?
   VM이 다르다고 해도, C나 Java 둘다 결국 String, Int 쓸텐데 데이터 왔다 갔다 할때마다 내가 다 매번 해줘야 하는게 말이 됨?!
   어차피 데이터 타입은 정해져 있고, 네트워크 코드 함짜두면 계속 쓸 수 있을텐데... --> 그래서 `데이터 교환을 위한 네트워크 코드를 정의해둔 인터페이스`가 `AIDL` 입니다.
