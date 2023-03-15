## Kotlin의 lazy와 lateinit의 차이

1. 초기화 시점:
- `lazy`: 변수를 처음 사용할 때 초기화 됨
- `lateinit`: 변수를 나중에 초기화 할 수 있음

2. 변수 유형:
- `lazy`: `val` 변수에 대해서만 사용 가능
- `lateinit`: `var` 변수에 대해서만 사용 가능

3. 예외 처리:
- `lazy`: 변수 초기화 과정에서 예외가 발생하면, 다시 초기화를 시도하지 않음
- `lateinit`: 변수를 초기화하지 않고 접근하면 `UninitializedPropertyAccessException` 예외 발생

4. 초기화 방법:
- `lazy`: 람다식을 사용하여 변수를 초기화
- `lateinit`: 변수를 사용하기 전에 어떤 방식으로 초기화할지 사용자가 결정해야 함

5. Thread safety:
- `lazy`: 기본적으로 스레드 안전(thread-safe) 함
- `lateinit`: 스레드 안전성은 보장되지 않음

6. 초기화 여부 체크:
- `lazy`: `isInitialized()` 메소드를 사용하여 변수가 초기화 되었는지 확인 가능
- `lateinit`: `isInitialized()` 메소드를 사용하여 변수가 초기화 되었는지 확인 가능

7. 사용 가능한 시기:
- `lazy`: 변수가 사용 가능한 시점은 초기화 이후임
- `lateinit`: 변수가 사용 가능한 시점은 초기화 이후임
