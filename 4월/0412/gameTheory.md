# 게임 이론

게임 이론은 항상 똑같이 해결이 가능한 문제

1. 한 사람이 먼저 시작한다.
2. 번갈아 가면서 최선의 수를 선택한다.

## 이해 및 풀이

1. `상대`에게 `질 수 밖에 없는 패`를 줄 수 있다면 승리한다.

### 예시

https://www.acmicpc.net/problem/9656

처음에 돌이 n 개 주어진다고 해보겠습니다.

1개 또는 3개를 가져갈 수 있습니다.

1 2 3 4 5 6 7 8 9 10

1 1 3 3 3 1 3 SK
1 1 1 3 3 CY
1 1 1
1

o x o x o x o

https://www.acmicpc.net/problem/9657

1개 또는 3개 또는 4개를 가져갈 수 있습니다.

마지막 돌을 가져가면 승리입니다.

`돌을 2개 상대에게 줄 수 있다면 승리입니다.`

```
1   2   3   4   5   6   7   8   9   10

1   1   3   4   3   4   3   1
    1           1   1   4   4
                1   1       3
    x                   x   x
```

2개 줄 수 없다면 패배
