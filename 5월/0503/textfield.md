# 실습

`text field` 의 상태와 초기화는 어떻게 해야할까요?

## 코드읽기

아래 코드는 계속 초기화가 되고 있어서 텍스트 수정이 되고 있지 않습니다.

따라서, `remember`로 똑같이 처리해줘서 초기화 시에 이전 정보를 사용하도록 바꿔줍니다.

또, `context = LocalContext.current`를 사용해서 Toast에도 정상적으로 context를 전달해줍니다.

## 코드

```kotlin
class TextFieldActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting6("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting6(name: String) {
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = "", onValueChange = {

        })
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = { }) {
            Text("click")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview6() {
    Compose_Theme {
        Greeting6("Android")
    }
}
```

수정 후

```kotlin

@Composable
fun Greeting6(name: String) {
    var textValue by remember {
        mutableStateOf("")
    }
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = textValue, onValueChange = {
            textValue= it
        })
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = { }) {
            Text("click")
        }
    }
}
```

토스트 추가

```kotlin
@Composable
fun Greeting6(name: String) {
    var textValue by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(value = textValue, onValueChange = {
            textValue= it
        })
        Spacer(modifier = Modifier.padding(10.dp))
        Button(onClick = {
            Toast.makeText(context,"${textValue}", Toast.LENGTH_SHORT).show()
        }) {
            Text("click")
        }
    }
}
```

### 기타

구조분해
