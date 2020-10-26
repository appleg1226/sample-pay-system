# Payment Service Module

## API 

#### 1. POST /payment/ : 결제 요청
Body Parameter:
~~~
String userId;
String shopId;
int money;
boolean doesUsePoint;
int usingPoint;
LocalDateTime dateTime;
~~~
ResponseBody
~~~
String userId;
String shopName;
long money;
~~~

## 궁금한 점
- 데이터베이스를 하나의 서버에서만 관리하는 게 좋을까, 아니면 여러 서버가 같은 데이터베이스를 공유해도 상관이 없나?
- 실제 결제 상황에서 유저와 매장 기기 두 개는 어떻게 response를 받을 것인가.  
  하나는 api 요청으로 받으면 되지만, 나머지 하나는 어떻게 응답을 처리해야 할까. 두 개를 연동시킬 수 있나?