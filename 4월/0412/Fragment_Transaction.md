일반적으로, `commit()` 보다는 `commitAllowingLossState()`를 사용합니다.

왜냐하면, `onSaveInstanceState()`비정상적인 종료 후에,`Fragment Transaction`이 호출될때 발생하는 오류를 방지해주기 때문입니다.

`.replce`는 모두 삭제 후 교체합니다.
`.add`는 백스택에 쌓습니다.
`.remove`는 제거
