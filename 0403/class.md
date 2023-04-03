# 0403 수업

```
[kotlin]companion = [java]static
```


## ListView vs RecyclerView

ListView에 넣어주는 역할이 일단 어댑터
```
Adapter

- GetView로 순서대로 정보를 가져와주고 [ Title, Description, img, ... ]

- XML로 바꿔준다. ( inflate )

```

view라는 타입은 코틀린의 최상위 클래스임

inflate시켜서 가져다 붙일건데, 