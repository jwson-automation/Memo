# 실습 1

일반 변수

## 코드 읽기

기존의 심플한 컴포즈 코드에 아래 변수를 2개 생성했습니다.
`var expanded = false`
`var extraPadding = if(expanded) 48.dp else 0.dp`
하지만, 바로 적용되지 않습니다!

왜냐하면 다시 그리더라도 expanded를 다시 false로 바꿔버리기 때문입니다.

따라서, `mutableStateOf`와 `Remember`라는 함수를 사용합니다.

`var expanded = remember{ mutableStateOf(false)}`

하지만, 여기서 참지 못한 개발자들이 by를 사용해서 더 쉽게 표현하겠다는 놈들이 나타났습니다.

https://kotlinlang.org/docs/delegated-properties.html

`Delegate`를 사용하면 그 변수의 setter와 getter를 만들어줍니다.

그래서 아래 코드를 by로 변경해줘서 더 깔끔한 코드를 만들어 줄 수 있습니다. ( get value와 set value 임포트 )
`var expanded = remember{ mutableStateOf(false)}`
`var expanded by remember{ mutableStateOf(false)}`

텍스트도 아래와 같이 쉽게 조건문으로 가능합니다.
`Text(if (expanded) "show less" else "show more")`

여기에서 remember를 remembersavable으로 바꿔줌으로써 Destroy에서 데이터가 소멸하지 않도록 지켜주는 것도 가능하다!
`var expanded by rememberSaveable{ mutableStateOf(false) }`

## 실습 코드

```kotlin
@Composable
fun Greeting3(name: String) {
    var expanded = false
    var extraPadding = if(expanded) 48.dp else 0.dp

    Surface(color = MaterialTheme.colors.secondary, modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(bottom = extraPadding)) {
                Text(text = "Hello,")
                Text(text = name)
            }
            Button(onClick = {
                expanded = !expanded
            }) {
                Text(if (expanded) "show less" else "show more")
            }
        }
    }
}
```

수정 후

```kotlin
@Composable
fun Greeting3(name: String) {
    var expanded = remember{ mutableStateOf(false)}
    var extraPadding = if(expanded.value) 48.dp else 0.dp

    Surface(color = MaterialTheme.colors.secondary, modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(bottom = extraPadding)) {
                Text(text = "Hello,")
                Text(text = name)
            }
            Button(onClick = {
                expanded.value = !expanded.value
            }) {
                Text(if (expanded) "show less" else "show more")
            }
        }
    }
}
```

delegate 후

```kotlin
@Composable
fun Greeting3(name: String) {
    var expanded by remember{ mutableStateOf(false)}
    var extraPadding = if(expanded) 48.dp else 0.dp

    Surface(color = MaterialTheme.colors.secondary, modifier = Modifier.padding(vertical = 4.dp)) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(bottom = extraPadding)) {
                Text(text = "Hello,")
                Text(text = name)
            }
            Button(onClick = {
                expanded = !expanded
            }) {
                Text(if (expanded) "show less" else "show more")
            }
        }
    }
}
```
