import sys

input = sys.stdin.readline
# 처음에는 스스로에게 사이클을 만들어줍니다.
par = [i for i in range(1000010)]
rank = [0 for _ in range(1000010)]
size = [1 for _ in range(1000010)]

def _find(x):
    while par[x] != x:
        x = par[x]
    return x

# union by rank 해줍니다.
def _union(a,b):
    a = _find(a)
    b = _find(b)

    if a == b:
        return
    
    if rank[a] < rank[b]:
        par[a] = b
        size[b] += size[a]
    elif rank[b] < rank[a]:
        par[b] = a
        size[a] += size[b]
    else:
        par[a] = b
        size[b] += size[a]
        rank[b] += 1

n = int(input())

for i in range(n):
    inp = list(input().split())

    if inp[0] == "I":
        _union(int(inp[1]),int(inp[2]))
    else:
        print(size[_find(int(inp[1]))])