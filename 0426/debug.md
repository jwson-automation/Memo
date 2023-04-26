# 디버깅

ViewHolder 크리에이팅 시에 아래 코드는 뷰 크기를 wrap contents로 자동으로 만들어 버린다.

```
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreReviewHolder {
        val binding: ReviewListBinding =
            ReviewListBinding.inflate(LayoutInflater.from(parent.context))
        return StoreReviewHolder(binding)
    }
```

ViewHolder 크리에이팅 시에 아래 코드는 뷰 크기를 아래와 같이 3단으로 짜주면 쉽게 해결된다.

```
override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StoreReviewHolder {
        val binding: ReviewListBinding =
            ReviewListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreReviewHolder(binding)
    }

```
