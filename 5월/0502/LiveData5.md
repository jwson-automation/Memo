# 실습 5

라이브 데이터를 변형 후 다시 라이브 데이터로 되돌리기

`Transformations.map()`을 사용하면 됩니다.

옵저브 가능하게 바꿔줍니다.

```
activityWithFragmentViewModel.times.observe(viewLifecycleOwner) {
        }
```

## 코드

```kotlin
 fun printCount() {
//        binding.textResult.text = activityWithFragmentViewModel.count.toString()
        activityWithFragmentViewModel.count.observe(viewLifecycleOwner) {
            binding.textResult.text = it.toString()
        }
        activityWithFragmentViewModel.times.observe(viewLifecycleOwner) {
            binding.times1.text = it.toString()
        }

    }
```

```kotlin
val times = Transformations.map(count){
        "$it * 2 = ${it*2}"
    }
```
