# 구조분해

## 코드 읽기

mutableStateOf 안에도 아래 코드와 같이 `component1,2`가 들어있습니다.

하지만, value가 하나인데 component는 2개가 있고, 둘 중 하나는 setter 입니다! 그렇기 때문에 할당을 이용해서!

```
   var (text,setText) = remember {
        mutableStateOf("")    }

    var textValue by remember {
        mutableStateOf("")    }
```

이렇게 예쁘게 표현할 수 있습니다.

또 아래 코드도 더 깔끔하게 변경 가능합니다!

```
TextField(value = text, onValueChange = {
            setText(it)
        })

        TextField(value = text, onValueChange = setText)
```

## 코드

```kotlin
data class Person(
    var id:Int,
    var name:String,
    var age:Int
)
fun main(){
    val person = Person(1, "정우", 14)

    //구조분해할당(Destructuring)
    var (a,b,c) = person
    println("id: $a, name : $b")
}
```

바이트코드

```kotlin
public final class TestKt {
   public static final void main() {
      Person person = new Person(1, "정우", 14);
      int a = person.component1();
      String b = person.component2();
      int c = person.component3();
      String var4 = "id: " + a + ", name : " + b;
      System.out.println(var4);
   }
```

구조 분해 할당 이용

```
@Composable
fun Greeting6(name: String) {
    var (text,setText) = remember {
        mutableStateOf("")    }
//    var textValue by remember {
//        mutableStateOf("")
//    }
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = text, onValueChange = {
            setText(it)
        })
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = {
            Toast.makeText(context,"${text}", Toast.LENGTH_SHORT).show()
        }) {
            Text("click")
        }
    }
}
```
