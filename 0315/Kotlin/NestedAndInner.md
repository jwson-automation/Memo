# 코틀린의 Nested Class와 Inner Class의 차이점

## Nested Class란?

Nested Class란 다른 클래스 내부에 정의된 클래스를 말합니다.

## Inner Class란?

Inner Class란 바깥 클래스와 연결된 클래스를 말합니다. 바깥 클래스의 인스턴스에 대한 참조를 가지고 있으며, 인스턴스 멤버를 가질 수 있습니다.

## Nested Class와 Inner Class의 차이점

Nested Class와 Inner Class의 가장 큰 차이점은 바깥 클래스에 대한 참조의 유무입니다. Nested Class는 바깥 클래스에 대한 참조를 가지고 있지 않습니다. Inner Class는 바깥 클래스에 대한 참조를 가지고 있습니다.

## 예제

### Nested Class 예제

```kotlin
class Outer {
    class Nested {
        fun example() {
            println("This is an example of Nested Class.")
        }
    }
}

val nested = Outer.Nested()
nested.example()
```

### Inner Class 예제

```kotlin
Copy code
class Outer {
    private val welcomeMessage: String = "Welcome to Inner Class!"
    inner class Inner {
        fun example() {
            println(welcomeMessage)
        }
    }
}

val outer = Outer()
val inner = outer.Inner()
inner.example()
```

위 예제에서 볼 수 있듯이, Nested Class는 바깥 클래스의 인스턴스에 대한 참조가 없으므로 외부 클래스의 멤버 변수에 접근할 수 없습니다. 반면에 Inner Class는 바깥 클래스의 인스턴스에 대한 참조를 가지므로 바깥 클래스의 private 멤버 변수에도 접근할 수 있습니다.

따라서, Nested Class와 Inner Class 중에서 어떤 것을 사용할지는 상황에 따라 다릅니다. Nested Class는 외부 클래스의 인스턴스에 대한 참조가 필요 없는 경우에 사용하고, Inner Class는 외부 클래스의 인스턴스에 대한 참조가 필요한 경우에 사용합니다.
