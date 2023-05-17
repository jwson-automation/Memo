import sys
sys.setrecursionlimit(99999)

input = sys.stdin.readline

def dfs(node,prv):
    for nxt in relation[node]:
        if nxt == prv:
            continue

        # 자식에게 이동 -- ↑
        dfs(nxt, node)
        # 부모에게 이동 -- ↓

        # 자식들의 숫자
        child[node] += child[nxt]

n,root,q = map(int,input().split())
relation = [[] for _ in range(n+1)]
child = [1 for _ in range(n+1)]

# 입력
for i in range(n-1):
    a, b = map(int,input().split())
    relation[a].append(b)
    relation[b].append(a)


# 출력
dfs(root, -1)


for _ in range(q):
    answer = int(input())
    print(child[answer])