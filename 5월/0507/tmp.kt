
val viewModel : MainViewModel by lazy {

    ViewModelProvider(this).get(MyViewModel::class.java) 
    
    }
    
    - val viewModel : ViewModel by lazy {
    
    ViewModelProvider(this)[(MyViewModel::class.java] 
    
    }
    
    - val viewModel : ViewModel by lazy {
    
    ViewModelProvider(this, object : ViewMOdelProvider.Factory{
     ovverride fun< T: ViewModel > create(modelClass:Class<T>): T {
    return ViewModel(0) as T
    }
    
    }
    
    }
    
    - val viewModel : ViewModel by viewModels()