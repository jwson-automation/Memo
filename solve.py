def recur(idx,pre):
    if pre > 1 :
        return -9999999
    
    if idx > n :
        return -9999999
    
    if dp[idx] != -1:
        return dp[idx]

    dp[idx] = max((recur(idx+1,pre+1) + stair[idx]), (recur(idx+2,0) + stair[idx]))

    return dp[idx]

n = int(input())
stair = [0]
dp = [-1 for _ in range(n+1)]
for _ in range(n):
    score = int(input())
    stair.append(score)

answer = recur(1,0)

print(answer)

