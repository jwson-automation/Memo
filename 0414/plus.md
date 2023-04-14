# 기억할 메소드, 기능

## 좌표값을 주소로 바꿔주기

`geocoder` : 위경도값을 주소값으로 바꿔주는 메소드
( 도로명 주소 / 구주소가 있으면 다 좌표 주소로 변경 가능?)
( 지도 데이터는 변수가 많습니다. 그래서 GPS -> 주소는 쉽다. 반대는 어렵다)

## 위치 이동 애니메이션

일반 이동 > 애니메이션 이동
`moveCamera()` > `animateCamera()`

## 위치 아이콘 가져오기, 변경하기

`theme`를 바꿔주면 아이콘도 변경된다.
`ResourceCompat.getDrawble(resources, R.drawble.id.location_icon, theme) as BitmapDrawable`

## 길게 눌러 위치 정보 뽑기

위경도 정보를 바꾸는건 불가능하지만, 누른 곳에 마커를 옮기고, 해당 위치로 포커스를 옮기는 것은 가능하다.
`setOnMapLongClickListener` 누르면 해당 위치의 위/경도 정보가 나옵니다.
