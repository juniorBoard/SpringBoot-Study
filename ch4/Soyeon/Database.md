## ORM, JPA, 영속성, 데이터베이스, 엔티티
- ORM ( Object Relational Mapping(객체-관계-매핑) )
    - 직접 SQL을 작성하지 않고도 객체지향 방식으로 DB에 접근

![image](https://user-images.githubusercontent.com/74857364/190863963-bf53e431-b81a-4419-9151-47feba17eb06.png)

객체와 데이터베이스의 관계를 매핑해주는 도구

ORM은 프로그래밍 언어의 객체와 관계형 데이터베이스의 데이터를 자동으로 매핑(연결)해주는 도구이다.

ORM은 MVC 패턴에서 모델(Model)을 기술하는 도구

- 사용 이유           
    - ORM을 이용해서 데이터베이스 접근을 프로그래밍 언어의 관점에서 맞출 수 있다.           
            
- 장점           
    - ORM을 이용하면 SQL Query 가 아닌 메서드로 데이터를 조작할 수 있다.           
      DBMS에 대한 종속성 저하           
           
- 단점
    - 완벽한 ORM 으로만 서비스를 구현하기 어렵다.           
           
[[데이터베이스] ORM이란? (Node.js ORM Lib Sequelize 소개)](https://hanamon.kr/orm%EC%9D%B4%EB%9E%80-nodejs-lib-sequelize-%EC%86%8C%EA%B0%9C/)

<br><br><br>

- JPA
  JPA는 ORM을 사용하기 위한 인터페이스를 모아둔 것
<br>

    - Hibernate

      Hibernate는 JPA의 대표적인 프레임워크

      JPA는 Interface의 모음이며 이를 구현한 구현체로 **Hibernate**, **EclipseLink**, **DataNucleus** 등 여러 ORM framework가 존재하는데 주로 **Hibernate를** 사용한다.
  
  <br>

    - **Spring Data JPA**
        - Spring Data JPA 란 JPA를 추상화시킨 Repository Interface를 제공하여 개발자가 JPA를 더 편하게 사용할 수 있게 하는 모듈

<br>

- 영속성 컨텍스트(Persistence Context)
    - 엔티티를 영구히 저장하는 환경

    - EntityManagerFactory

<br><br><br>

- 데이터베이스
    - 데이터베이스를 한 마디로 정의하면 **데이터의 집합**이라고 할 수 있다.

  <br>

    - DBMS
        - 이런 데이터베이스를 관리하고 운영하는 소프트웨어를 DBMS(Database Management System) 라고 한다.
      
        - 다양한 데이터가 저장되어 있는 데이터베이스는 여러 명의 사용자나 응용 프로그램과 공유하고 동시에 접근이 가능해야 한다.
      
        - 종류 : MySQL, 오라클(Oracle), SQL 서버, MariaDB
      
        - 유형 : 계층형(Hierarchical), 망형(Network), 관계형(Relational), 객체지향형(Object-Oriented),
          객체관계형(Object-Relational) 등으로 분류된다.

      현재 사용되는 DBMS 중에는 관계형 DBMS가 가장 많은 부분을 차지하며,
      MySQL도 관계형 DBMS에 포함된다.

      <br>
  
        - 관계형 DBMS
            - 관계형 DBMS(Relational DBMS)는 줄여서 RDBMS라고 부른다.                               
              MySQL뿐만 아니라, 대부분의 DBMS가 RDBMS 형태로 사용된다.          

              RDBMS의 데이터베이스는 테이블(table)이라는 최소 단위로 구성되며, 이 테이블은 하나 이상의 열(column)과 행(row)으로 이루어져 있다.

      <br>
      
        - SQL : DBMS에서 사용하는 언어                                         
          구조화된 질의 언어라는 뜻으로 관계형 데이터베이스에서 사용되는 언어                          
          표준 SQL을 배우면 대부분의 DBMS를 사용할 수 있다.                                     

<br>

  [[데이터베이스 이해하기] Database(DB), DBMS, SQL의 개념]([https://hongong.hanbit.co.kr/데이터베이스-이해하기-databasedb-dbms-sql의-개념/](https://hongong.hanbit.co.kr/%EB%8D%B0%EC%9D%B4%ED%84%B0%EB%B2%A0%EC%9D%B4%EC%8A%A4-%EC%9D%B4%ED%95%B4%ED%95%98%EA%B8%B0-databasedb-dbms-sql%EC%9D%98-%EA%B0%9C%EB%85%90/))

<br>
<br>
<br>

*영속화 : 영속화는 단순하게 설명하면 애플리케이션의 데이터가 애플리케이션의 프로세스보다 더 오래 지속하게끔 하려는 것을 말한다.            
Java 용어로 말하면, 객체의 상태가 JVM의 범위를 넘어서서 지속하여 추후에 동일한 상태를 이용하려는 것이다.           

[ORM(Object/ Relational Mapping)에 대해서]([http://www.notforme.kr/archives/993](http://www.notforme.kr/archives/993))

---

- JPA : ORM 기술 표준으로 채택된 인터페이스의 모음 ( = 기술 명세서 )

- Spring Data JPA 란 JPA를 추상화시킨 Repository Interface를 제공하여 개발자가 JPA를 더 편하게 사용할 수 있게 하는 모듈            
  → JPA를 추상화 시킨게 Repository Interface
 
- ORM을 구현하기 위한 인터페이스(JPA) != JPA를 추상화 시킨 Repository Interface(Spring Data JPA)           

- JPA 구현체가 Hibernate(ORM framework)           

- JPA(인터페이스)를 구현해둔 Hibernate(ORM)           
  Repository인터페이스를 Spring Data JPA가 JPA화하고(JPA로 번역해준다) 그 JPA를 Hibernate로 실행하는 것 같다.

- JPA를 추상화한게 Spring Data JPA이고 Spring Data JPA가 Repository를 제공           

*추상화 : 불필요한 것을 지우고 핵심을 남겨둔다. (추상화 라는 단어가 어렵다면? → 반대로 생각하면 구체화)           

*인터페이스 : 완전한 추상화를 제공한다.           

관련 글 >> [Controller, Service, Repository](https://haedal-uni.github.io/posts/Controller,-Service,-Repository/) 
           
---

ORM, JPA랑 EntityManager, Persistence Context는 무슨 관계?           
Jpa 에서 앤티티매니저팩토리를 통해 각 트랜잭션 수만큼 엔티티매니저를 만들어서 영속성 데이터를 관리한다.           

           
→ **객체와 관계형 데이터 매핑**           
→ **EntityManager (Entity 관리)**           
→ **Persistence Context (Entity 저장소)**           