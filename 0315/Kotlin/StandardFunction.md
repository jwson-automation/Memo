# Kotlin의 표준 함수

## 목차

1. [let](#let)
2. [run](#run)
3. [with](#with)
4. [apply](#apply)
5. [also](#also)

### let

```kotlin
val result = someNullableVar?.let {
    // 코드 블록
}
```

### run

```kotlin
val result = someObject.run {
    // 코드 블록
}
```

### with

```kotlin
val result = with(someObject) {
    // 코드 블록
}
```

###apply

```kotlin
val result = someObject.apply {
    // 코드 블록
}
```

###also

```kotlin
val result = someObject.also {
    // 코드 블록
}
```

## 추가 설명

### let

`let` 함수는 수신 객체를 람다 인자로 받아 실행하고, 람다의 결과를 반환합니다.

```kotlin
val numbers = listOf(1, 2, 3, 4, 5)
val evenNumbers = numbers.filter { it % 2 == 0 }.let {
    println("짝수 개수: ${it.size}")
    it
}
```

### run

run 함수는 수신 객체 람다를 실행하고 결과를 반환합니다.

```kotlin
val result = "Hello, World!".run {
    this.toUpperCase()
}
```

### with

with 함수는 객체를 인자로 받아 해당 객체를 수신 객체 람다로 사용하고, 람다의 결과를 반환합니다.

```kotlin
data class Person(val name: String, val age: Int)

val person = Person("Alice", 30)
val introduction = with(person) {
    "제 이름은 $name이고, 나이는 $age살입니다."
}
```

### apply

apply 함수는 수신 객체 람다를 실행하고, 수신 객체를 반환합니다.

```kotlin
data class Person(var name: String, var age: Int)

val person = Person("Alice", 30).apply {
    name = "Bob"
    age = 25
}
```

### also

also 함수는 수신 객체를 람다 인자로 받아 실행하고, 수신 객체를 반환합니다.

```kotlin
val numbers = mutableListOf(1, 2, 3, 4, 5)
val result = numbers.also {
    it.add(6)
    it.remove(1)
}
```

## 확장 함수와 표준 함수의 조합

Kotlin의 표준 함수들을 확장 함수와 함께 사용하여 코드를 더 간결하고 읽기 쉽게 만들 수 있습니다.

### 예시 1: String 확장 함수와 let

```kotlin
fun String?.notEmptyOrNull(): String? {
    return if (this.isNullOrEmpty()) null else this
}

val input: String? = "Kotlin"
val result = input.notEmptyOrNull()?.let {
    "입력값: $it"
}
```

### 예시 2: List 확장 함수와 also

```kotlin
fun <T> MutableList<T>.addIfNotNull(element: T?): MutableList<T> {
    element?.also { this.add(it) }
    return this
}

val numbers = mutableListOf(1, 2, 3, 4, 5)
val newNumber: Int? = 6
numbers.addIfNotNull(newNumber)
```

### 표준 함수의 체이닝

여러 표준 함수들을 함께 사용하여 코드를 체인 형태로 작성할 수 있습니다.

### 예시 1: let과 also 체이닝

```kotlin
data class Person(var name: String, var age: Int)

val person: Person? = Person("Alice", 30)

val result = person?.let {
    it.name = "Bob"
    it.age = 25
    it
}.also {
    println("이름: ${it?.name}, 나이: ${it?.age}")
}
```

### 예시 2: apply와 run 체이닝

```kotlin
data class Product(var name: String, var price: Double)

val product = Product("샘플 상품", 100.0).apply {
    price *= 0.9
}.run {
    "${name}의 할인 가격: ${price}원"
}
```

## Kotlin 표준 함수와 코루틴

Kotlin 코루틴은 비동기 작업을 처리할 때 유용하게 사용할 수 있는 기능입니다. 표준 함수와 함께 사용하면 더 간결한 코드를 작성할 수 있습니다.

### 예시 1: 코루틴과 withContext

```kotlin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun performNetworkRequest(): String {
    // 네트워크 요청을 수행하는 코드
    return "Network response"
}

suspend fun main() {
    val networkResponse = withContext(Dispatchers.IO) {
        performNetworkRequest()
    }.let {
        "네트워크 응답: $it"
    }

    println(networkResponse)
}
```

### 예시 2: 코루틴과 launch, also

```kotlin
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.delay

fun CoroutineScope.printWithDelay(delayTime: Long, message: String) = launch {
    delay(delayTime)
    println(message)
}.also { it.join() }

suspend fun main() {
    CoroutineScope(Dispatchers.Default).printWithDelay(1000L, "1초 후 메시지 출력")
}
```

### 정리

Kotlin 표준 함수를 사용하면 코드를 더 간결하고 가독성이 좋게 작성할 수 있습니다. 표준 함수들을 함께 사용하거나 다른 Kotlin 기능들과 조합하여 다양한 방식으로 코드를 작성할 수 있습니다. 이를 통해 Kotlin 코드를 더욱 효율적으로 작성하고 유지보수하기 쉽게 만들 수 있습니다.
