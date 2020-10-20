# User Service Module

## API 

#### 1. GET /user/{id} : 유저 정보 조회
#### 2. GET /user/exchanges/all/{id} : 유저의 모든 거래 내역 조회
#### 3. GET /user/exchanges/payment/{id} : 유저의 결제 내역 조회
#### 4. GET /user/exchanges/send/{id} : 유저의 송금 내역 조회
#### 5. GET /user/exchanges/send/to/{id} : 유저의 송금만 조회
#### 6. GET /user/exchanges/send/from/{id} : 유저의 받은 송금만 조회
#### 7. GET /user/exchanges/send/not-completed/{id} : 아직 완료되지 않은 송금 조회
#### 8. POST /user/register/card/{id} : 유저의 카드 등록
#### 9. POST /user/register/bank/{id} : 유저의 계좌 등록
#### 10. POST /user/charge/card/{id} : 카드로 잔액 충전
#### 11. POST /user/charge/bank/{id} : 계좌로 잔액 충전
#### 12. GET /shop/{id} : 매장의 정보 조회

## 궁금한 점
- CARD와 BANK를 나누지 않고 어떻게 한 컨트롤러로 만드는 게 나을까, 나누는 게 나을까.
- package 구조 과연 최선일까. 도메인별로 분류하는 것은 어떠한지.