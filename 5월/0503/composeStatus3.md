# 실습 3

## 재사용

## 코드

```kotlin
class LazyActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Compose_Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greetings5("Android")
                }
            }
        }
    }
}
@Composable
fun Greetings5(name: String,
    names:List<String> = List(100){"$it"}) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)){
        items( items = names){ name ->
            Greet(name)
        }
    }
}



@Composable
fun Greet(name: String) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    val extraPadding = if( expanded ) 48.dp else 0.dp

    Surface(color = MaterialTheme.colors.secondary, modifier = Modifier.padding(vertical = 4.dp)) {
        Row(modifier = Modifier
            .padding(24.dp)
            .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            Column(modifier = Modifier.padding(bottom = extraPadding)) {
                Text(text = "Hello,")
                Text(text = name)
            }
            Button(onClick = {
                expanded = !expanded
            }) {
                Text(if(expanded) "show less" else "show more")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview5() {
    Compose_Theme {
        Greetings5("Android")
    }
}
```
