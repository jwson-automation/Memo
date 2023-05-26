# https://www.acmicpc.net/problem/17304

def _find():
    pass
def _union():
    pass

n, m = map(int,input().split())
relation = [[] for _ in range(n+1)]
double = [[] for _ in range(n+1)]
visited = [0 for _ in range(n+1)]

# 단방향 양방향 분리
for _ in range(m):
    a, b = map(int,input().split())

    if a in relation[b]:
        relation[b].remove(a)
        double[a].append(b)
        double[b].append(a)
    else:
        relation[a].append(b)

# 단방향 방문 처리 완료
for i in relation:
    for j in i:
        visited[j] = 1

# 양방향 그래프 트리 확인
# 트리인가?
# 사이클이 있는가?
# 방문 처리가 포함 되어 있는 트리인가?
for i in range(1, n+1):
    pass


print(relation)
print(double)
print(visited)
