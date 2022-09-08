
객체와 관계형 데이터 매핑에서는
엔티티 매니저(entity 관리)와 Persistence Context(Entity 저장소) 라는게 있다.       

영속성 컨텍스트는 orm, jpa, hibernate랑은 관계가 없고 영속성 컨텍스트는 엔티티 매니저만 들어가는 얘기다.       

엔티티 매니저에서 데이터 베이스에 접근해서 curd 작업을 수행한다는 얘기가            
entity가 db crud 처리를 하는게 사실은 엔티티 매니저가 하는 것이라는 얘기이다.      

→ Jpa 에서 앤티티매니저팩토리를 통해 각 트랜잭션 수만큼 엔티티매니저를 만들어서 영속성 데이터를 관리한다.  

https://haedal-uni.github.io/posts/Controller,-Service,-Repository/
