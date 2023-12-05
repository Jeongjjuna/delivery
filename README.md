# 배달어플 API

## 1. Project Structure
```
기본경로 : main ──> java ──> com ──> sjincho ──> hun

main...
├── hun
│    ├── auth
│    ├── common
│    ├── config
│    ├── delivery
│    ├── exception
│    ├── food
│    ├── member
│    ├── order
│    │      
│    ├── DeliveryApplication.java
│
test...
├── hun
     ├── config
     ├── delivery
     ├── food
     ├── member
     ├── order
```
### main
- auth
  - controller : 인증중 발생하는 에러를 처리하는 핸들러와, 로그인 REST API 컨트롤러를 구현하고 있다.
  - dto : 인증정보를 담는 UserDetails와 로그인 요청 정보를 담은 LoginRequest를 포함한다.
  - exception : 인증과정에서 발생할 수 있는 커스텀 에러들을 포함하고 있다.
  - service : 로그인 service와, 인증 정보를 가져오는 UserDetailService, jwt관련 기능 서비스를 구현하고있다.
  - filter : 토큰의 인증을 검사하는 필터를 구현하고있다.
- common : Entity의 공통필드를 BaseEntity로 관리한다.
- config : Security, Swagger 설정파일을 관리한다.
- exception
  - DeliveryApplicationException : 런타임중에 발생하는 예외를 관리한다.
  - ErrorResponse : 에러 응답의 구체적인 형태를 관리한다.
  - GlobalControllerAdvice : 애플리케이션에서 발생하는 예외를 핸들링한다.
- delivery : 배달관련 REST API를 구현
  - controller, service, repository, dto, domain, exception으로 구성
- food : 음식상품관련 REST API를 구현
  - controller, service, repository, dto, domain, exception으로 구성
- member : 회원관련 REST API를 구현
  - controller, service, repository, dto, domain, exception으로 구성
- order : 주문 REST API를 구현
  - controller, service, repository, dto, domain, exception으로 구성

### test
- config : 테스트시 인증에 필요한 MockUser가 설정되어있다.
- delivery : domain단위테스트와, controller통합테스트를 구현
- food : domain단위테스트와, controller통합테스트를 구현
- member : domain단위테스트와, controller통합테스트를 구현
- order : domain단위테스트와, controller통합테스트를 구현





## 2. REST API

### 음식(구현완료)
- 판매 음식 전체 조회 - `GET /foods` - 판매점, 사용자
- 판매 음식 조회 - `GET /foods/{id}` - 판매점, 사용자
- 판매 음식 등록 - `POST /foods` - 판매점
- 판매 음식 수정 - `PUT /foods/{id}` - 판매점
- 판매 음식 삭제 - `DELETE /foods/{id}` - 판매점

### 회원(구현완료)
- 전체 회원 조회 - `GET /members` - 판매점
- 회원 조회 - `GET /members/{id}` - 판매점
- 내 정보 조회 - `GET /members/my` - 판매점, 사용자
- 회원 등록 - `POST /members`
- 회원 수정 - `PUT /members/{id}` - 판매점, 사용자
- 회원 삭제 - `DELETE /members/{id}` - 판매점, 사용자

### 주문(구현완료)
- 전체 주문 조회 - `GET /orders` - 판매점
- 주문 조회 - `GET /orders/{id}` - 판매점
- 사용자의 주문 조회 - `GET /orders/members/{id}` - 판매점
- 현재 접수중인 모든 주문 조회 -  `GET /orders/accepting` - 판매점
- 주문 하기 - `POST /orders` - 사용자
- 주문 취소 - `PATCH /orders/{id}/cancel` - 사용자
- 주문 수락 - `PATCH /orders/{id}/accept` - 판매점
- 주문 거절 - `PATCH /orders/{id}/reject` - 판매점


### 배달(구현완료)
- 전체 배달 조회 - `GET /deliveries` - 판매점
- 배달 조회 - `GET /deliveries/{id}` - 판매점
- 현재 배달중인 모든 배달 조회 - `GET /deliveries/delivering` - 판매점
- 배달 등록 - `POST /deliveries` - 판매점
- 배달 시작 - `PATCH /deliveries/{id}/start` - 판매점
- 배달 완료 - `PATCH/deliveries/{id}/complete` - 판매점
- 배달 취소 - `DELETE /deliveries/{id}` - 판매점


### 인증(구현완료)
- 로그인 - `POST /auth/login`



---
## 3. ERD
![table_diagram.png](docs%2Fimages%2Ftable_diagram.png)

## 4. 도메인
![entity_diagram.png](docs%2Fimages%2Fentity_diagram.png)