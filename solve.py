def recur(idx):

    if idx == 2 :
        return False

    cnt = 0
    for i in range(3):

        if recur(idx-i) == False:
            cnt += 1

    return recur(idx)
    
n = int(input())
numbers = [1,3,4]
recur(n)

# 먼저 2로 만들 수 있는지 보기, 없다면 pass

# 



# 1,3,4를 버리고 줬을 때 2를 만들수 있냐 없냐를 꺼내야함.

# 누군가가 2를 만들었다고 하면 그때 승패가 결정된다.