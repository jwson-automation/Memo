#코틀린의 data class

코틀린에서 `data class`는 데이터 저장을 위해 사용됩니다. 

## 특징

- `equals()`, `hashCode()`, `toString()` 등의 메서드를 자동으로 생성합니다.
- 프로퍼티에 대한 `component1()`, `component2()` 등의 메서드를 자동으로 생성합니다.
- `copy()` 메서드를 사용하여 객체를 복사할 수 있습니다.

## 예제

```kotlin
data class Person(val name: String, val age: Int)
```

위와 같이 data class를 선언하면, 다음과 같은 메서드들이 자동으로 생성됩니다.

```kotlin
equals()
hashCode()
toString()
component1()
component2()
copy()
```
이제 다음과 같이 Person 객체를 생성하고, toString() 메서드의 결과를 확인해보겠습니다.

```kotlin
val person = Person("Alice", 29)
println(person) // 출력 결과: "Person(name=Alice, age=29)"
```

equals() 메서드를 사용하여 객체를 비교할 수도 있습니다.

```kotlin
Copy code
val person1 = Person("Alice", 29)
val person2 = Person("Alice", 29)
println(person1 == person2) // 출력 결과: true
```
component1(), component2() 등의 메서드를 사용하여 프로퍼티에 접근할 수 있습니다.

```kotlin
Copy code
val person = Person("Alice", 29)
val name = person.component1()
val age = person.component2()
println("name: $name, age: $age") // 출력 결과: "name: Alice, age: 29"
```
copy() 메서드를 사용하여 객체를 복사할 수 있습니다.

```kotlin
Copy code
val person = Person("Alice", 29)
val personCopy = person.copy()
println(personCopy) // 출력 결과: "Person(name=Alice, age=29)"
```
위와 같이 코틀린에서 data class를 사용하면, 객체의 생성과 관련된 메서드들을 간단하게 구현할 수 있습니다.
