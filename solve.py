# def dfs(y,x):  # depth return으로 빼기
#     for i in range(4):
#         ey = y + dy[i]
#         ex = x + dx[i]

#         if 0 <= ey < n and 0 <= x < n:
#             if visited[ey][ex] : continue
#             return dfs(ey,ex) + 1
#     return 0

def recur(y, x):
    visited = [[0 for _ in range(n)]for _ in range(n)]
    for i in range(4):
        ey = y + dy[i]
        ex = x + dx[i]

        if 0 <= ey < n and 0 <= ex < n:
            if visited[ey][ex]:
                continue
            if graph[y][x] >= graph[ey][ex]:
                continue
            visited[ey][ex] = 1
            return max(dp[ey][ex], recur(ey, ex) + 1)

    return 0


dy = [0, 1, 0, -1]
dx = [1, 0, -1, 0]

n = int(input())
graph = [list(map(int, input().split())) for _ in range(n)]
dp = [[-1 for _ in range(n)] for _ in range(n)]
for y in range(n):
    for x in range(n):
        dp[y][x] = recur(y, x)
print(dp)
