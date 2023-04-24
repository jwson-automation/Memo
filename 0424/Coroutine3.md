# CoroutineScope

launch = builder

- Coroutine은 Kotlin과 별개의 개념입니다.
- Python에서도 사용할 수 있습니다.

## 메소드

### `GlobalScope.launch{ }`

: 일반적으로 사용을 권장하지 않습니다.
: application 전체 life cycle의 전체 범위에 해당되는 스코프 하나를 시작!

### `CoroutineScope.launch{}`

- Coroutine은 딱히 Thread를 block 하지 않습니다. 코루틴이 실행중일 때 프로그램을 종료되면 그냥 그대로 끝입니다.

### `runBlocking{}`

: 현재 스레드를 못 끝내도록 멈춰두기 + 코루틴 생성을 해주는 친구입니다.
: 이것도 코루틴 안에서 사용을 권장하지 않습니다!
: 보통 테스트 할 때 자주 사용합니다!

- Coroutine 속 Coroutine을 만들때, 단순히 launch를 하면 부모의 속성을 따라가는 코루틴이 생성됩니다. 아래 예시

```koltin
// 그냥 다른 두개의 코루틴임
runBlocking{
GlobalScope.launch{}
}

// 같은 속성의 코루틴임
runBlocking{
launch{}
}
```

### `delay()`

- Coroutine은 `delay`로 지연시킬 수 있습니다.
- 일반 메소드에서는 사용할 수 없습니다. Coroutine에서만 사용 가능합니다.
  ( 일반 메소드에서 쓰면 오류 문구와 함께 suspend 해야 한다고 나옵니다.)
