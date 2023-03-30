def recur(number, n, cnt):
    global answer

    if cnt > answer:
        return

    if n < 0:
        return

    if n == 0:
        answer = min(cnt, answer)
        return

    if number <= 0:
        return

    recur(number, n-(number**2), cnt+1) # 빼주거나
    recur(number-1, n, cnt) # 넘어가거나

# 가능한만큼 빼줄겁니다.
# 그 다음으로 넘어가줄거에요
# 그냥 넘어갈 수도 있습니다.

n = int(input())
# dp = [0 for _ in range(int(n**0.5)+1)]

answer = 1e9

recur(int(n**0.5), n, 0)

print(answer)
