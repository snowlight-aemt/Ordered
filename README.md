# Ordered

## 여러 강의에서 학습한 방법을 적용한 학습 프로젝트 
* 비즈니스 성공을 위한 java/spring 기반 서비스 개발과 MSA 구축 강의 | 베이스 코드로 학습
* 현실세상의TDD실전편:설계확장성을위한 코드개선방법 강의 | 테스트 기법 적용
* Spring Cloud로 개발하는 마이크로서비스 애플리케이션(MSA) 강의 | DevOps 구축 

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

### 결제 메시지
* 큐 메시지 (AWS SQS) 를 사용하여 통신  
![gift-aws-sqs](https://user-images.githubusercontent.com/82430645/230725087-cbcb5403-4a99-45e2-9faf-62ea7fd748a1.png)

### 선물 제품 주문
* 주문 도메인의 기능을 일부 재사용하여 주문 기능 구현  
![gift-order](https://user-images.githubusercontent.com/82430645/230725010-9e0645da-6012-456c-a076-6730b8f1632e.png)

### 선물 제품 수락
* 선물을 수락하여 배송지 정보를 입력  
![gift-order-accept](https://user-images.githubusercontent.com/82430645/230768894-19d75555-76f4-4e18-a02b-a7ae702da795.png)

### 관련 서비스 저장소
[Ordered](https://github.com/snowlight-aemt/Ordered). 

[Ordered-Gift](https://github.com/snowlight-aemt/Ordered-Gift). 

[Ordered-Discover](https://github.com/snowlight-aemt/Ordered-discover-server). 

[Ordered-Gateway](https://github.com/snowlight-aemt/Ordered-api-gateway-server). 

### 일정 관리

## 참고 URL
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%ED%81%B4%EB%9D%BC%EC%9A%B0%EB%93%9C-%EB%A7%88%EC%9D%B4%ED%81%AC%EB%A1%9C%EC%84%9C%EB%B9%84%EC%8A%A4/dashboard
https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-JPA-%EC%9B%B9%EC%95%B1  
https://fastcampus.co.kr/dev_red_ygw  
https://fastcampus.co.kr/dev_red_lhc  
