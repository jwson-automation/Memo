def recur(idx):
    
    pass


n = int(input())

#물약 가격
posion_price = list(map(int, input().split()))

#할인 정보
info = {}

for posion in range(n):
    info[posion] = []
    p = int(input())
    for _ in range(p):
        a, d = map(int, input().split())
        info[posion].append([a, d])
    
recur(0)

