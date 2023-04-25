def check(): # 연결 가능한지 확인
    pass

n = int(input())
relation = [[] for _ in range(100)]
for _ in range(n):
    type, x, y = map(int,input().split())

    if type == 1:
        relation[x].append(y)
        relation[y].append(x)

    if type == 2:
        if check():
            print(1)
        else:
            print(0)
        