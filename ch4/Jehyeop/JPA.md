# JPA(Java Persistence API)
- JPA는 ORM을 사용하기 위한 인터페이스를 모아둔 것
- ORM 기술 표준으로 채택된 인터페이스의 모음  ( = 기술 명세서 )  
  → 단순 명세로 구현체가 없다.
- ORM 이 큰 개념이라면 JPA 는 더 구체화된 스펙을 포함한다.

### 특징
- 내부적으로 JDBC 를 사용한다. (데이터베이스에 접속할 수 있도록 하는 자바 API)
- 개발자 대신 SQL 을 생성하고 데이터베이스를 조작해 객체를 자동 매핑 하는 역할을 수행한다.
- JPA 기반의 구현체 : EclipseLink, Hibernate, DataNucleus

### JPA 구동 방식

1. `Persistence` Class → (*META-INF/persistence.xml 읽어오기*) → `EntityManagerFactory` Class 생성(*application 로딩 시점에 생성* )
2. `EntityManagerFactory` Class → `EntityManager` 들 생성 (*트랜잭션 단위 마다 생성*)  
   **EntityManager** : 쓰레드간 공유x, 사용후 폐기  
   JPA 의 모든 데이터는 ***트랜잭션** 안에서 실행