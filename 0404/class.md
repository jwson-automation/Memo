# 0404

ToolBar, 다양한 알림

## Toolbar vs Actionbar vs Appbar

- 버전에 따라서 차이가 있습니다.
- 롤리팝까지(Action bar)

### 실습

1. 일단 기존의 툴 바를 제거합니다. `theme.xml` > `NoActionBar`
2. toolbar를 findViewById로 가져옵니다. `물론 xml에서 수정해도 됩니다.`
3. 메뉴를 추가하고 싶다면, 메뉴를 추가하고 연결합니다. `inflateMenu(R.menu.-)`
   [주의!] `+ onCreateOptonsMenu`와 다릅니다! `onOptionsItemSelected`로 수정할 수 없습니다.
4.
