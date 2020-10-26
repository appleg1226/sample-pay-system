# sample-pay-system

## 개요
기존 페이 시스템의 기능을 모방하여 주요 기능들의 로직을 작성하는 연습 프로젝트.  
확장성 있는 시스템 개발 / 테스트 주도 개발을 실천하며 진행하는 프로젝트.  
이번에는 특별히 블로그에 개발 과정을 기록하며 진행 중이다. 
https://appleg1226.tistory.com/3
<br/>

## 사용 예정인 기술 스택
- dev: java 8, maven, kotlin, gradle, Spring boot, spring security(jwt), spring batch, spring caching, 
       spring data mongo, spring cloud
- middle: RabbitMQ(docker), Redis(docker), MongoDB(docker), ELK Stack(docker)
- ci/cd: docker, jenkins, kubernetes, aws(ec2), github
<br/>

## 페이 시스템 개요
- 페이머니 충전  
충전기능.  
카드/은행 계좌를 이용하여 충전하는 두 가지 기능이 있음.

- 결제 / 포인트 적립  
매장에서는 유저의 바코드를 인식하여 결제가 이루어진다.  
생체인증으로 결제 전에 확인이 필요하다.  
주문금액/결제수단/결제일시/상호명/페이승인id 등의 정보가 고지된다.  
결제하면 포인트가 적립된다.  
이 포인트는 이후 결제할 때 1000포인트 이상일 경우 현금처럼 사용할 수 있다.   

- 송금  
계좌번호로 송금하는 기능.  
만약 상대도 회원일 경우에는 계좌번호 없이 계정만으로 송금 가능 / 해당 회원의 계좌로도 입금 가능  
예약송금기능도 존재.  
송금을 진행했을 경우 실질적인 처리는 상대방이 승인을 하는 경우에 이루어진다.  
이 때 두 계좌의 금액이 이동하게 된다.  

- 이용내역 조회  
월별로 이용내역 조회: 사용처/금액/잔액/사용내용  
이용내역은 3년이 지나면 제거하도록 설정  

- 멤버십  
해당 계정의 바코드로 다른 멤버십을 적립/사용할 수 있음.  
유저는 여러 멤버십들을 등록/수정/삭제가 가능해야함.  
운영자는 멤버십 목록을 업데이트할 수 있음.  

- 리포트 전송(배치)  
유저에게 이달의 사용기록에 대하여 일정 주기마다 리포트 전송하기.  
각 가게에 정산하고 그 기록은 따로 보관할 것.  
<br/>

## 모듈 설명

### user-service
유저 데이터 조회, 결제 수단 등록, 충전 기능 등을 담당한다. 

### pay-service
결제 서비스를 담당한다.

### transfer-service(Kotlin)
송금 서비스를 담당한다. Kotlin으로 작성했다.

### service-detection
Spring Cloud Eureka를 이용한 서비스 detection 서버

### api-gateway
Spring Cloud Zuul을 이용한 gateway 서버

