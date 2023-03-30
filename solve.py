def recur(y, x):

    if dp[y][x] != -1:
        return dp[y][x]    # dp값이 업데이트 되었다면 거기서 끝

    cnt = 0
    for i in range(4):  # dfs탐색을 하면서, 내리막길 이동을 한다.
        ey = dy[i] + y
        ex = dx[i] + x

        if not 0 <= ey < ay or not 0 <= ex < ax:
            continue
        if graph[y][x] <= graph[ey][ex]:
            continue

        cnt += recur(ey, ex)

    dp[y][x] = cnt  # 돌아오면서 해당 위치의 값을 dp로 저장한다.

    return dp[y][x]  # 더이상 나아갈 곳이 없으면 그 위치의 값을 가져온다.

# 4방 이동을 하면서, 끝가지 도달했다면 1을 가지고 돌아오고, 한 위치에서 모든 이동 결과는 경우의 수다.
# 따라서, 모든 이동 결과가 dp로써 저장되었다는 말은 더이상 갈 필요가 없다는 뜻이다!
# 그래서 우측 아래부터 쭉쭉쭉 dp값이 저장되는 것.


dy = [0, 1, 0, -1]
dx = [1, 0, -1, 0]

ay, ax = map(int, input().split())
graph = [list(map(int, input().split())) for _ in range(ay)]
dp = [[-1 for _ in range(ax)] for _ in range(ay)]

dp[ay-1][ax-1] = 1  # 마지막은 반드시 1개임, 이걸 가져와서 업데이트 해주기 때문에

print(recur(0, 0))

# 2차원은 1차원으로 보고 경우의수로 해준다.
# 결과론적으로는 2차원 DP가 되어야 할 것 같다.

# 내리막길을 갈거다.
