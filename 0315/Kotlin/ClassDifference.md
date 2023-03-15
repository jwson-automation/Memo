# Kotlin의 Object Class와 Data Class, Enum Class를 비교

## Object Class

`Object Class`는 Singleton 패턴을 쉽게 구현할 수 있는 기능을 제공합니다.

```kotlin
object Singleton {
    // ...
}
```

## Data Class

`Data Class`는 equals(), hashCode(), toString(), copy() 메서드를 자동으로 생성해주며, 프로퍼티를 간단하게 정의할 수 있습니다.

```kotlin
data class User(val name: String, val age: Int)
```

## Enum Class

`Enum Class`는 서로 관련 있는 상수들을 하나의 타입으로 정의할 수 있습니다.

```kotlin
Copy code
enum class Color(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}
```

이 외에도 Object Class는 Companion Object를 통해 클래스 내부의 정적 메서드나 프로퍼티를 구현할 수 있습니다.

Data Class는 componentN() 메서드를 자동으로 생성해주어, 객체 분해를 편리하게 구현할 수 있습니다.

Enum Class는 각 상수의 이름, 순서, 값을 구할 수 있는 메서드와 values() 메서드를 제공합니다.

위와 같이, Kotlin에서 제공하는 Object Class, Data Class, Enum Class는 각각의 특징을 가지고 있으며, 상황에 따라 적절히 사용할 수 있습니다.
