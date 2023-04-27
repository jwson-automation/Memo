1. Fragment를 3초의 딜레이를 주고 A또는 B가 커밋되도록 한다.

2. 3초의 시간이 지나기 전에 뒤로가기를 눌러버린다.

3. 뒤로가기(Destroy) 후에 commit이 발생하기때문에 오류가 나온다.

`commitAllowingStateLoss()`를 사용하면 나오지 않는다! 왜냐! 어차피 닫혔고, 무시해도 되는 오류니까!

---

이게 중요한 이유가 바로, Fragment의 한계를 보여주는 예시이기 때문이다. `commitAllowingStateLoss()`라는 함수가 불완전한 Fragment를 증명하는 증거이기 때문에 재미있다!!!!
