import sys
input = sys.stdin.readline

n = int(input())
table = [[] for _ in range(n + 1)]

for i in range(n):
    a, b = map(int, input().split())
    table[i + 1] = [a, b]

dp = [0 for _ in range(n + 1)]


for idx in range(1,n)[::-1]:
    if idx + table[idx][0] > n:
        dp[idx] = dp[idx+1]
    else:
        dp[idx] = max(dp[idx+1], dp[idx + table[idx][0]] + table[idx][1])

print(dp)
print(max(dp))