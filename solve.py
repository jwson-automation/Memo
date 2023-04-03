def dfs():


def recur(y,x):
    pass

n = int(input())
graph = [list(map(int, input().split())) for _ in range(n)]
dp = [[-1 for _ in range(n)] for _ in range(n)]
for y in range(n):
    for x in range(n):
        recur(y,x)
