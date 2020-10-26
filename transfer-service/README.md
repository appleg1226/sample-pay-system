# Transfer Service Module(Kotlin project)

## Kotlin 1.3.72

## API 
#### 1. POST /transfer/ : 송금 요청
Body Parameter:
~~~
String senduUerId;
String receiveUserId;
long money;
LocalDateTime dateTime;
~~~
ResponseBody: Exchange.kt
~~~
String paymentId
Enum exchangeType
String myId
String otherId
Long money
LocalDateTime exchangeDate
boolean isComplete
~~~
#### 2. POST /transfer/complete/{transfer-id} : 송금 확정
Parameter:
~~~
String transfer-id : 송금 요청 시 발급된 송금 아이디
~~~
Response: 성공시 200(OK) 반환