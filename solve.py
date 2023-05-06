from collections import deque

T = int(input())
for t in range(T):
    N, K = map(int, input().split())
    time = list(map(int, input().split()))
    count = [0] * N
    relation = [[] for _ in range(N)]


    for i in range(K):
        X, Y = map(int, input().split())
        relation[X-1].append(Y-1)
        count[Y-1] += 1

    W = int(input()) - 1

    result = [0] * N

    Q = deque()

    for i in range(N):
        if count[i] == 0 : # 0 인경우에
            Q.append(i)

    while count[W] > 0:
        u = Q.popleft()
        for next in relation[u]:
            result[next] = max(result[next], result[u]+time[u])
            count[next] -= 1
            if not count[next]:
                Q.append(next)

    print(result[W]+time[W])