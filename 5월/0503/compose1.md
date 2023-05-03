# 실습 1

UI를 예쁘게 잘 문법에 맞게 정리하면 됩니다! syntax의 영역

## 코드

```kotlin

class ColumnActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    Greeting2(name = "jwson")
                    MyApp()
                }
            }
        }
    }
}


@Composable
fun Greeting2(name: String) {
    androidx.compose.material.Surface(color = MaterialTheme.colors.secondary) {
        Row(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column() {
                Text(text = "hello")
                Text(text = "$name")
            }
            Button(
                onClick = { /*TODO*/ },
                border = BorderStroke(1.dp, Color.Red),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color.Yellow,
                    contentColor = Color.Blue,
                )

            ) {
                Text(text = "Click me")
            }
        }
    }
}


@Composable
fun MyApp(
    modifier: Modifier = Modifier,
    names: List<String> = listOf("World", "Compose")
) {
    Column() {
        for (_name in names)
            Greeting2(name = _name)
        Box(
            modifier = Modifier
                .background(color = Color.Green)
                .width(300.dp)
                .height(300.dp)
        ) {
            Text("Hello", fontSize = 24.sp)
            Text("nice to meet you", modifier = Modifier.align(Alignment.BottomEnd))

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                Text("Hello", fontSize = 24.sp, fontStyle = FontStyle.Italic)
            }
        }
    }



}

@Preview(showBackground = true, widthDp = 320)
@Composable
fun DefaultPreview2() {
    Compose_Theme {
        Greeting2("world")
    }
}

@Preview(showBackground = true)
@Composable
fun MyPreview() {
    Compose_Theme {
        Box(
            modifier = Modifier
                .background(color = Color.Green)
                .width(300.dp)
                .height(300.dp)
        ) {
            Text("Hello", fontSize = 24.sp)
            Text("nice to meet you", modifier = Modifier.align(Alignment.BottomEnd))

            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
                Text("Hello", fontSize = 24.sp, fontStyle = FontStyle.Italic)
            }
        }
    }
}
```
