def recur(idx,pre):
    if pre > 2 :
        return -9999999
    
    if idx > n :
        return -9999999
    
    # if idx == n :
    #     return stair[idx]
    
    if dp[idx] != -1:
        return dp[idx]

    dp[idx] = max(stair[idx],(recur(idx+1,pre+1) + stair[idx]), (recur(idx+2,1) + stair[idx]))

    return dp[idx]

n = int(input())
stair = [0]
dp = [-1 for _ in range(n+1)]
for _ in range(n):
    score = int(input())
    stair.append(score)


print(recur(0,0))
print(dp)

