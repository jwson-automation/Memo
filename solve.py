n,m,k = map(int,input().split())
price = list(map(int,input().split()))
relation = [[] for _ in range(n)]
visited = [-1 for _ in range(n)]

for _ in range(m):
    v,w = map(int,input().split())
    relation[v-1].append(w-1)
    relation[w-1].append(v-1)

print(relation)

# k는 내가 가진 돈
# price는 필요한 돈
# 친구의 친구는 친구다.

answer = 0

def dfs(idx):
    visited[idx] = 1
    for j in relation[idx]:
        visited[j] = 1

def recur(idx,result):
    
    recur(idx+1, result)
    recur(idx+1, result + price[idx])
    
recur(0)