# ConstraintLayout

## 개요

```
1. 4방향 모두 지정하는 것을 권장합니다.
2. 세로방향의 match_paent는 0dp라고 적습니다.
...
```

https://developer.android.com/reference/android/support/constraint/ConstraintLayout#developer-guide

## bias

위치 지정

```
0 : 왼쪽에 붙이기
0.5 : 가운데
1 : 오른쪽에 붙이기
```

## chain

`2023-03-17 09 38 07.png`

```
Spread Chain
Weighted Chain
Packed Chain
Packed Chain with Bias
```

## 실습

```
1. 여러 오브젝트를 함께 선택해서 우클릭으로 체인을 연결해 줄 수 있습니다.

2. Button의 BackGroundColor를 바꾸기 위해서는 BackgroundTint='@null'로 해줘야 합니다.

3. 커스텀하기 위해서는 `AppCompat`이 들어간 AppCompatButton을 사용하면 좋다.

4. Show Base Line을 이용해서 Text 크기가 다를 때 위치도 잡아줄 수 있다.
```
