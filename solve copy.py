def recur(idx,pre):
    print(idx,pre)
    # 불가능한 경우
    if pre > 2 :
        return -9999999
    
    # 범위를 벗어난 경우
    if idx > n :
        return 0
    
    # 이미 계산된 경우
    if dp[idx] != -1:
        return dp[idx]

    # 1칸 올랐을 때와 2칸 올랐을 떄
    dp[idx] = max(recur(idx+1,pre+1) + stair[idx], recur(idx+2,1) + stair[idx])

    return dp[idx]

n = int(input())
stair = [0]
dp = [-1 for _ in range(n+1)]
for _ in range(n):
    score = int(input())
    stair.append(score)

print(recur(0,0))
print(dp)

