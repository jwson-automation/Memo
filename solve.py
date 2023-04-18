n = int(input())

arr = list(map(int,input().split()))
dp = [1 for _ in range(n)]
dp2 = [1 for _ in range(n)]

for i in range(1, n):
    for j in range(i):
        if arr[i] > arr[j]:
            dp[i] = max(dp[i], dp[j]+1)


arr2 = []
for k2 in range(n)[::-1]:
    arr2.append(arr[k2])

for i2 in range(1, n):
    for j2 in range(i2):
        if arr2[i2] > arr2[j2]:
            dp2[i2] = max(dp2[i2], dp2[j2]+1)

# print(dp)
# print(dp2)

answer = 0
for k in range(n):
    answer = max(answer, dp[k] + dp2[n-k-1]-1)

print(answer)
