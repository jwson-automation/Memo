# 백트래킹 - 탑다운 - 바텀업

n = int(input())

graph = [list(map(int,input().split())) for _ in range(n)]
dp = [[0 for _ in range(3)] for _ in range(2)]


for k in range(3):
    dp[0][k] = graph[0][k]

for i in range(1,n):
    for j in range(3): # RGB
        if j == 0:
            dp[1][j] = min(dp[0][1] + graph[i][j], dp[0][2] + graph[i][j])
        if j == 1:
            dp[1][j] = min(dp[0][0] + graph[i][j], dp[0][2] + graph[i][j])
        if j == 2:
            dp[1][j] = min(dp[0][1] + graph[i][j], dp[0][0] + graph[i][j])
    for f in range(3):
        dp[0][f] = dp[1][f]
    
print(dp)