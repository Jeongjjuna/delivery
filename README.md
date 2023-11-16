# 배달어플 API

URL / HTTP Method


### 음식(구현완료)
- 판매 음식 전체 조회 - `GET /foods` - 판매점, 사용자
- 판매 음식 조회 - `GET /foods/{id}` - 판매점, 사용자
- 판매 음식 등록 - `POST /foods` - 판매점
- 판매 음식 수정 - `PUT /foods/{id}` - 판매점
- 판매 음식 삭제 - `DELETE /foods/{id}` - 판매점

### 회원
- 전체 회원 조회 - `GET /members` - 판매점
- 회원 조회 - `GET /members/{id}` - 판매점, 사용자
- 회원 등록 - `POST /members` - 판매점, 사용자
- 회원 수정 - `PUT /members/{id}` - 판매점, 사용자
- 회원 삭제 - `DELETE /members/{id}` - 판매점, 사용자

### 주문
- 전체 주문 조회 - `GET /orders` - 판매점
- 주문 조회 - `GET /orders/{id}` - 판매점
- 주문 하기 - `POST /orders` - 사용자
- 주문 수락 - `POST /orders/{id}/accept` - 판매점
- 주문 수정 - `PUT /orders/{id}` - 사용자
- 주문 거절 - `DELETE /orders/{id}` - 판매점
- 사용자의 주문 조회 - `GET /orders/members/{id}` - 판매점, 사용자

### 배달
- 전체 배달 조회 - `GET /deliveries` - 판매점
- 배달 조회 - `GET /deliveries/{id}` - 판매점, 사용자
- 배달 시작 - `POST /deliveries` - 판매점
- 배달 취소 - `DELETE /deliveries/{id}` - 판매점
- 배달 완료 - `POST /deliveries/{id}/complete` - 판매점

### 리뷰/(별점)
- 전체 리뷰 조회 - `GET /reviews/foods/{id}` - 판매점, 사용자
- 리뷰 작성 - `POST /reviews` - 사용자
- 리뷰 수정 - `PUT /reviews/{id}` - 사용자
- 리뷰 삭제 - `DELETE /reviews/{id}` - 사용자


---
## MySQL 테이블

```
MySQL Table : food
+-----------+-------------+------+-----+---------+-------+
| Field     | Type        | Null | Key | Default | Extra |
+-----------+-------------+------+-----+---------+-------+
| id        | int         | NO   | PRI | NULL    |       |
| name      | varchar(50) | NO   |     | NULL    |       |
| price     | int         | NO   |     | NULL    |       |
| food_type | varchar(50) | NO   |     | NULL    |       |
+-----------+-------------+------+-----+---------+-------+
```

```
MySQL Table : member
+-------------+--------------------------+------+-----+---------+----------------+
| Field       | Type                     | Null | Key | Default | Extra          |
+-------------+--------------------------+------+-----+---------+----------------+
| id          | bigint                   | NO   | PRI | NULL    | auto_increment |
| cell_phone  | varchar(255)             | NO   |     | NULL    |                |
| email       | varchar(255)             | NO   | UNI | NULL    |                |
| member_role | enum('CUSTOMER','OWNER') | NO   |     | NULL    |                |
| name        | varchar(255)             | NO   |     | NULL    |                |
| password    | varchar(255)             | NO   |     | NULL    |                |
+-------------+--------------------------+------+-----+---------+----------------+
```