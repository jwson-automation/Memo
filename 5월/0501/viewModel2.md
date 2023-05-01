# ViewModel

_주의_ - viewModel 안에는 context 저장하면 안됩니다.
만약에 꼭 해야겠다면, 권장하지 않지만, Android Viewmodel을 사용해서 하는 것이 가능은 합니다!
(Application을 parameter로 넘겨서 생성하는 것이 가능합니다.)

## View Model 초기화 방법

1. Parameter을 넘겨서 초기화

ViewModelPrivider.Factory{
...
}

2. viewModels()에서 초기화

ViewModelPrivider.Factory{
...
}
