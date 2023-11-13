# 배달어플 API

URL / HTTP Method


---
## [손님 기준]
### 음식
- 음식 상품 전체 조회 - `GET /foods`
- 음식 상품 개별 조회 - `GET /foods/{id}`
```
MySQL Table : food
+----------+-------------+------+-----+---------+-------+
| Field    | Type        | Null | Key | Default | Extra |
+----------+-------------+------+-----+---------+-------+
| id       | int         | NO   | PRI | NULL    |       |
| name     | varchar(50) | NO   |     | NULL    |       |
| price    | int         | NO   |     | NULL    |       |
| foodType | varchar(50) | NO   |     | NULL    |       |
+----------+-------------+------+-----+---------+-------+
```


---
### 주문
- 음식 주문 하기 - `POST /orders/foods/{id}`
- 주문한 음식 조회 - `GET /orders/{id}`
- 음식 주문 취소 - `DELETE /orders/{id}`


---

### 회원
- 회원 등록 하기 - `POST /users`
- 회원 조회 하기 - `GET /users/{id}`
- 회원 수정 하기 - `PUT /orders/{id}`
- 회원 삭제 하기 - `DELETE /orders/{id}`


---
## [매장기준]
### 음식
Map 자료구조 DB를 활용하여 Controller, Service, Repository 기본틀 구현 완료
- 판매할 음식 전체 조회 - `GET /admin/foods`
- 판매할 음식 조회 - `GET /admin/foods/{id}`
- 판매할 음식 등록 - `POST /admin/foods`
- 판매할 음식 수정 - `PUT /admin/foods/{id}`
- 판매할 음식 삭제 - `DELETE /admin/foods/{id}`

```
MySQL Table : food
+----------+-------------+------+-----+---------+-------+
| Field    | Type        | Null | Key | Default | Extra |
+----------+-------------+------+-----+---------+-------+
| id       | int         | NO   | PRI | NULL    |       |
| name     | varchar(50) | NO   |     | NULL    |       |
| price    | int         | NO   |     | NULL    |       |
| foodType | varchar(50) | NO   |     | NULL    |       |
+----------+-------------+------+-----+---------+-------+
```

---
### 주문받은 상품
- 주문된 음식 전체 조회 - `GET /admin/orders`
- 주문된 음식 개별 조회 - `GET /admin/orders/{id}`
- 주문 수락하기 - `POST /admin/orders/{id}`
- 주문 거절하기 - `DELETE /admin/orders/{id}`

---
### 배달
- 배달시작하기 - `POST /admin/delivery/orders/{id}`
- 배달완료하기 - `POST /admin/delivery/{id}`


---
### 회원
- 모든 회원 조회 하기 - `GET /admin/users`
- 회원 조회 하기 - `GET /admin/users/{id}`
- 회원 수정 하기 - `PUT /admin/users/{id}`
- 회원 삭제 하기 - `DELETE /admin/users/{id}`