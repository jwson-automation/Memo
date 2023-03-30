def recur(y, x):
    global answer
    print(y, x)

    if y == ay-1 and x == ax-1:
        answer += 1
        return

    for i in range(3):
        ey = dy[i] + y
        ex = dx[i] + x

        if 0 <= ey < ay and 0 <= ex < ax:
            if graph[y][x] > graph[ey][ex]:
                recur(ey, ex)

    return


dy = [0, 1, 0]
dx = [1, 0, -1]

ay, ax = map(int, input().split())
graph = [list(map(int, input().split())) for _ in range(ay)]
count_graph = [[0 for _ in range(ax)] for _ in range(ay)]

answer = 0

recur(0, 0)

print(answer)

# 2차원은 1차원으로 보고 경우의수로 해준다.
# 결과론적으로는 2차원 DP가 되어야 할 것 같다.

# 내리막길을 갈거다.
# 이제 이걸 탑다운 DP로 바꿀 예정이다.

# y,x에 도달했을때, 뒤를 업데이트 하는 방법을 쓰면
