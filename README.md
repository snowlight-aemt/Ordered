# Ordered
Study MSA 공부를 위한 샘플 프로젝트 :)

## yml 설정
* Kakao Pay Token(KEY)

application-kakao.yml
```properties
spring:
  pay:
    kakao:
      credentials:
        admin-key: 
```

## 스팩
* Spring boot 3.0.3
* Java 17
* Jpa
* Eureka
* Aws SQS - Massage Queue 
* Mysql

### 테스트 도구
* FixtureMonkey
* Mockito
* [Localstask (Docker)](https://localstack.cloud/)
  * Sqs 로컬 테스트 
* awaitility

---

# 도메인 
### 서비스 구성도
![image](https://user-images.githubusercontent.com/82430645/228264176-d5a4dd75-b27d-45a1-9fd3-f98ab52f113f.png)

## 결제

### 결제 메시지
* 큐 메시지 (AWS SQS) 를 사용하여 통신  
![gift-aws-sqs](https://user-images.githubusercontent.com/82430645/230725087-cbcb5403-4a99-45e2-9faf-62ea7fd748a1.png)

### 선물 제품 주문
* 주문 도메인의 기능을 일부 재사용하여 주문 기능 구현  
![gift-order](https://user-images.githubusercontent.com/82430645/230725010-9e0645da-6012-456c-a076-6730b8f1632e.png)

### 선물 제품 수락
* 선물을 수락하여 배송지 정보를 입력  
![gift-order-accept](https://user-images.githubusercontent.com/82430645/230768894-19d75555-76f4-4e18-a02b-a7ae702da795.png)

### 카카오 페이 적용 
* 카카오 페이 구성
 
[Github - spring-kakao-pay-api](https://github.com/snowlight-aemt/test-code/tree/master/spring-kakao-pay-api)  
[Notion - Kakao Pay Api](https://www.notion.so/aemt/Kakao-Pay-Api-1-ceee3f51a9c745bfa4142f7be1938b00?pvs=4)

### 관련 서비스 저장소
[Ordered](https://github.com/snowlight-aemt/Ordered). 

[Ordered-Gift](https://github.com/snowlight-aemt/Ordered-Gift). 

[Ordered-Discover](https://github.com/snowlight-aemt/Ordered-discover-server). 

[Ordered-Gateway](https://github.com/snowlight-aemt/Ordered-api-gateway-server). 

### 일정 관리
https://aemt.notion.site/Ordered-665ea42f195040ccae55bfd3c303597b
