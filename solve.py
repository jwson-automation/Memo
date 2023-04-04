import sys
sys.setrecursionlimit(99999999)

def recur(n):
    if n == 0:
        return 0

    if dp[n] != -1:
        return dp[n]
    
    
    dp[n] = min(recur(n-(i**2)) + 1 for i in range(1, int(n**0.5)+1))
    return dp[n]


n = int(input())
dp = [-1 for _ in range(n+1)]

print(recur(n))
# print(dp)
