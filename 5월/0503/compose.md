# Compose

`compose` : 구성하다, 작곡하다

(2008.09) 안드로이드가 xml을 이용하는 형태로 출시

(2017.05) Flutter가 나옴

(2021.06?) Compose First Release

## 명령형 vs 선언형

명령형 : 모두 하나씩 명령을 내려주는 방식
선언형 : 명령의 묶음을 미리 선언 해둠으로써 쉽게 표현하는 방식

## Compose의 장점

- 코드 감소

- 호환성
  : 대부분의 라이브러리는 호환 가능

- 직관적 ( 선언형 )
  : 사실 그런데 xml도 충분히 직관적이긴 하다.. 음

## Compose 코드

`@Composable` 컴포즈코드 ( 함수 )
`@Preview` 미리보기
`modifier` 요소, 속성

`arrangement`
https://developer.android.com/reference/kotlin/androidx/compose/foundation/layout/Arrangement

`surface` 박스 기본형
`Box` 박스

```
composeOptions {
        kotlinCompilerExtensionVersion '1.1.1'
    }
```

### 기타

- Observeable은 jdk version 1부터 있었습니다.
