def recur(idx,pre):
    if pre > 2 :
        return -9999999

    if idx > n :
        return 0

    if dp[idx][pre] != -1:
        return dp[idx][pre]

    dp[idx][pre] = max(stair[idx],(recur(idx+1,pre+1) + stair[idx]), (recur(idx+2,1) + stair[idx]))

    return dp[idx][pre]

n = int(input())
stair = [0]
dp = [[-1,-1,-1] for _ in range(n+1)]
for _ in range(n):
    score = int(input())
    stair.append(score)

print(recur(1,0))
print(dp)