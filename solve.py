n = int(input())
arr = []

y_list = []
x_list = []
ans = [-1]*n

for _ in range(n):
    a,b = map(int,input().split())
    arr.append([a,b]) # x,y
    y_list.append(b) # 14 15 14 16
    x_list.append(a) # 14 16 15 15

for y in y_list:
    for x in x_list:
        dist = []
        for ex,ey in arr: # 1개 기준으로, 계산합니다.
            d = abs(ex-x) + abs(ey-y) # 두가지 x,y축을 향한 거리
            dist.append(d)

        dist.sort()

        print(f"dist:{dist}")

        tmp = 0 
        for i in range(len(dist)):
            d = dist[i]
            tmp += d
            if ans[i] == -1: 
                ans[i] = tmp
            else :
                ans[i] = min(tmp, ans[i])
            print(f"ans: {ans}")

print(*ans)

# 점들의 최단거리는, 점들 속에 있다. 14, 15, 16, 100, 200 이라는 숫자가 있다면 최단 거리는 그중 하나인 16이다.
# 그래서 먼저 만날 장소를 정해준다.
# 만날 장소에 각각의 좌표가 오도록 만든다.
# 가까운 순서대로 결과를 도출해낸다.

# 0. A는 15 14, B는 15 16이다
# 1. 15 14, 15 16 이라는 점에서 만날 수 있다.
# 2. 15 14라는 곳에 A가 0이라는 거리로 올 수 있기 때문에, dist = [0]이 저장된다. 
# 3. 15 14라는 곳에 A는 0으로 오고, 15 16은 2로 오기 떄문에 dist = [0,2]가 저장된다.
# 4. 1개의 점이 모일 때 거리는 0, 2개의 점이 모일때 거리는 2가 된다.
# 5. 여기에 정렬을 해주면 좌표의 숫자가 아무리 많아져도, 최소한의 거리가 되는 점들을 선택할 수 있게 된다.