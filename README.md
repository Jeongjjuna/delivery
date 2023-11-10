# 배달어플 API

URL / HTTP Method

## [손님 기준]
### 음식
- 음식 상품 전체 조회 - `GET /foods`
- 음식 상품 개별 조회 - `GET /foods/{id}`


### 주문
- 음식 주문 하기 - `POST orders/foods/{id}`
- 주문한 음식 조회 - `GET /orders/{id}`
  - ex) 배송준비중, 배송중, 배송완료
- 음식 주문 취소 - `DELETE /orders/{id}`


## [매장기준]
### 음식
- 판매할 음식 전체 조회 - `GET management/foods`
- 판매할 음식 조회 - `GET management/foods/{id}`
- 판매할 음식 등록 - `POST management/foods`
- 판매할 음식 수정 - `PUT management/foods/{id}`
- 판매할 음식 삭제 - `DELETE management/foods/{id}`


### 주문받은 상품
- 주문된 음식 전체 조회 - `GET management/orders`
- 주문된 음식 개별 조회 - `GET management/orders/{id}`
- 주문 수락하기 - `POST management/orders/{id}`
- 주문 거절하기 - `DELETE management/orders/{id}`

### 배달
- 배달시작하기 - `POST management/delivery/orders/{id}`
- 배달완료하기 - `POST management/delivery/{id}`