# Kotlin 고차 함수

고차 함수란 다른 함수를 매개 변수로 사용하거나 함수를 반환 값으로 사용하는 함수를 말합니다. Kotlin은 고차 함수를 지원하므로 함수를 더욱 간결하고 유연하게 작성할 수 있습니다.

## 람다 식

고차 함수를 작성할 때 람다 식을 사용할 수 있습니다. 람다 식은 간결하고 가독성이 높은 코드를 작성할 수 있도록 도와줍니다. 다음은 람다 식을 사용한 고차 함수의 예시입니다.

```kotlin
fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return operation(x, y)
}

val sum = calculate(4, 2) { a, b -> a + b } // sum = 6
val difference = calculate(4, 2) { a, b -> a - b } // difference = 2
```

## 함수 타입
Kotlin은 함수를 일급 객체로 취급합니다. 따라서 함수도 일반적인 값과 같이 다룰 수 있습니다. 함수 타입을 사용하면 함수를 매개 변수로 전달하거나 함수를 반환하는 등의 작업을 할 수 있습니다. 다음은 함수 타입을 사용한 고차 함수의 예시입니다.

```kotlin
fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {
    return operation(x, y)
}

fun add(x: Int, y: Int): Int {
    return x + y
}

val sum = calculate(4, 2, ::add) // sum = 6
```

## 커링
커링은 다중 인수를 갖는 함수를 단일 인수를 갖는 함수의 연속으로 바꾸는 기법입니다. Kotlin에서는 함수의 인수를 람다 식으로 전달하여 커링을 구현할 수 있습니다. 다음은 커링을 사용한 고차 함수의 예시입니다.

```kotlin
fun add(x: Int) = { y: Int -> x + y }

val increment = add(1)
val result = increment(5) // result = 6
```

## 고차 함수와 스코프 함수

Kotlin에는 고차 함수 외에도 스코프 함수라는 특별한 종류의 함수가 있습니다. 스코프 함수는 객체의 범위를 제한하여 해당 객체의 멤버에 쉽게 접근하도록 도와줍니다.

다음은 스코프 함수의 종류와 각각의 특징입니다.

### let

`let` 함수는 객체를 인자로 받고 람다 식을 실행한 결과를 반환합니다. 람다 식 내에서는 해당 객체를 `it` 키워드를 통해 접근할 수 있습니다.

```kotlin
val name: String? = "John"
val length = name?.let { it.length } ?: 0 // length = 4
```

### with
with 함수는 객체와 람다 식을 인자로 받습니다. 람다 식 내에서는 해당 객체를 this 키워드를 통해 접근할 수 있습니다.

```kotlin
val person = Person("John", 30)

val introduction = with(person) {
    "My name is $name and I am $age years old."
} // introduction = "My name is John and I am 30 years old."
```

### run
run 함수는 객체와 람다 식을 인자로 받습니다. 람다 식 내에서는 해당 객체를 this 키워드를 통해 접근할 수 있습니다. run 함수는 람다 식의 결과를 반환합니다.

```kotlin
val person = Person("John", 30)

val ageNextYear = person.run {
    age + 1
} // ageNextYear = 31
```

### apply
apply 함수는 객체와 람다 식을 인자로 받습니다. 람다 식 내에서는 해당 객체를 this 키워드를 통해 접근할 수 있습니다. apply 함수는 객체 자신을 반환합니다.

```kotlin
val person = Person("John", 30).apply {
    age = 31
}

// person.age = 31
```

### also
also 함수는 객체와 람다 식을 인자로 받습니다. 람다 식 내에서는 해당 객체를 it 키워드를 통해 접근할 수 있습니다. also 함수는 객체 자신을 반환합니다.

```kotlin
val person = Person("John", 30)

person.also {
    it.age = 31
}

// person.age = 31
```
### 확장 함수
Kotlin에서는 확장 함수를 사용하여 기존 클래스의 메서드를 확장할 수 있습니다. 확장 함수는 일반 함수와 같이 작성되지만, 수신 객체를 첫 번째 매개 변수로 받습니다.

```kotlin
fun String.toTitleCase(): String {
    return this.split(" ").joinToString(" ") {
        it.capitalize()
    }
}

val title = "hello, world!".toTitleCase() //
```