# Scaffold

: 건축기반, 틀

`TopBar, BottomBar, SnackBar, FAB, Drawer`

## 코드 읽기

drawer의 오픈 여부 저장을 위해서 아래 state를 저장해준다.

`val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))`

`scaffoldState = scaffoldState,`

## 코드

```kotlin

class ScreenWithScaffoldActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp3 {
                Greeting5("Android")
            }
        }
    }
}

@Composable
fun MyApp3(content: @Composable () -> Unit) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    Compose_Theme {
        // A surface container using the 'background' color from the theme
        Scaffold(
            scaffoldState = scaffoldState,
            topBar = {
                TopAppBar(
                    title = { Text("TopAppBar") },
                    backgroundColor = MaterialTheme.colors.primary
                )
            },
            floatingActionButtonPosition = FabPosition.End,
            floatingActionButton = {
                FloatingActionButton(onClick = {
                    scope.launch {
                        scaffoldState.snackbarHostState.showSnackbar("clicked..")
                    }
                }) {
                    Text("X")
                }
            },
            drawerContent = { Text(text = "drawerContent")},
            content = {
                content()
            },
            bottomBar = { BottomAppBar(backgroundColor = MaterialTheme.colors.primary) { Text("BottomAppBar") } }
        )
    }
}
```
