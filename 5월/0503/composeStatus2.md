# 실습 2

Lazy

만약에 다시 그려주는데, 칼럼이 100개 있으면 어떻게 하나요?

버벅거립니다.

따라서 보이는 부분만 랜더링 하게 해주는

`LazyColumn`을 사용합니다!

## 코드 읽기

column을 먼저 lazyColumn으로 바꿔줍니다.
`Column()` > `lazyColumn()`

`.verticalScroll(rememberScrollState())`를 주석 처리해줍니다.

화면만 가져오는 상태로 변합니다.

## 코드

```kotlin

class NonLazyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting4("Android4")
                }
            }
        }
    }
}

@Composable
fun Greeting4(name: String) {

    Column( modifier = Modifier
        .fillMaxSize()
    ){
        for(i in 1..50){
            Text("$name : $i")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DefaultPreview4() {
    Compose_Theme {
        Greeting4("Android4")
    }
}
```

수정 후

```kotlin

@Composable
fun Greeting4(name: String) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green)
//            .verticalScroll(rememberScrollState())
    ) {
        item {
            Text("Header", fontSize = 42.sp)
        }
        items(50) {
            Log.d(TAG, "Greeting4: $it")
            Text(text = "name : ${it}")
        }
        item {
            Text("Footer", fontSize = 42.sp)
        }
    }
}

```
