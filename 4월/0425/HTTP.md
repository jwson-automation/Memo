# NFC

## HTTP

: `H`yper`T`ext `T`ransfer `P`rotocol (`엄청난문자로 통신하는 규약!`)

- 링크를 눌러서 이동하는 것

### HTTP는 stateless

: 모든 요청에 그냥 대답을 할뿐, 상태를 저장하지 않습니다.

### REST API?

: `RE`presentational `S`tate `T`ransfer

- URI로 표현하는 방법
- 동사보다는 명사를 권장합니다.
- Is-a 관계가 아닌, has-a관계(포함성)

- 예 : 영화정보는 GET/movieinfo, 영화리스트는 GET/movies ,영화 업로드는 POST/movies 처럼 행위 기반으로 표현해 주는 방법

## Retrofit

Square에서 만든 서버와 HTTP 통신을 하기 위한 라이브러리 입니다.

Request를 처리하기 위해서 OkHttp를 사용합니다.
