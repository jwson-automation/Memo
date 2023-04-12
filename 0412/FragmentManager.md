# Fragment Manager

프래그먼트 안에 프래그먼트를 만들었다면?

`FragmentA` in `Activity` : Support Fragment Manager
`FragmentB` in `FragmentA` : Child Fragment Manager
`FragmentA` can call `FragmentB` : Parent Fragment Manager

## 기타

- 일단 `.add` 는 백스택을 추가해준다.

- onSaveInstanceState()

- commitAlienceLoss : 상태가 저장된 뒤에 커밋을 하면 오류가 나는데, 이걸 무시하고 보여주세요. -->
