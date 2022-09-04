# MyBatis

- Java 애플리케이션에서 SQL 데이터베이스 액세스를 구현하기 위해 가장 일반적으로 사용되는 오픈 소스 프레임 워크중 하나이다.
- 객체 지향 언어인 자바의 관계형 데이터베이스 프로그래밍을 좀 더 쉽게 할 수 있게 도와 주는 개발 프레임 워크이다.
- JDBC를 통해 데이터베이스에 엑세스하는 작업을 캡슐화하고 일반 SQL 쿼리, 저장 프로 시저 및 고급 매핑을 지원한다.
- 모든 JDBC 코드 및 매개 변수의 중복작업을 제거한다.
- Mybatis 에서는 프로그램에 있는 SQL쿼리들을 한 파일에 구성하여 프로그램 코드와 SQL을 분리할 수 있는 장점을 가지고 있다.
- DAO 와 SQL 문을 Mapping 해주는 Persistence Framework다.

### **특징**

- 복잡한 쿼리나 다이나믹한 쿼리에 강하다 - 반대로 비슷한 쿼리는 남발하게 되는 단점이 있다.
- **프로그램 코드와 SQL 쿼리의 분리**로 코드의 간결성 및 유지보수성 향상
- resultType, resultClass등 Vo를 사용하지 않고 조회결과를 사용자 정의 DTO, MAP 등으로 맵핑하여 사용 할 수 있다.
- 빠른 개발이 가능하여 생산성이 향상된다.

## **Mybatis3 구조**
![Untitled](https://user-images.githubusercontent.com/90807141/188309138-a8d6f250-7607-4456-89c7-768bec51d9a7.png)

![Untitled](https://user-images.githubusercontent.com/90807141/188309188-58191e5c-f93c-4d78-8f94-013b6efd6e7a.png)

### 주요 구성 요소

`MyBatis configuration file`

- MyBatis3의 작업 설정을 설명하는 **XML 파일**
- 데이터베이스의 연결 대상, 매핑 파일의 경로, MyBatis3의 작업 설정 등과 같은 세부 사항을 **설명**하는 파일
- Spring과 통합하여 사용할 때 데이터베이스의 연결 대상과 매핑 파일 경로 설정을 구성 파일에 지정할 필요가 없다.

  그러나 MyBatis3의 기본 작업을 변경하거나 확장 할 때 설정이 수행된다.


`org.apache.ibatis.session.SqlSessionFactoryBuilder`

- MyBatis3 구성 파일을 읽고 생성하는 SqlSessionFactory 구성 요소

`org.apache.ibatis.session.SqlSessionFactory`

- SqlSession을 생성하는 구성 요소

`org.apache.ibatis.session.SqlSession`

- SQL 실행 및 트랜잭션 제어를 위한 API를 제공하는 구성 요소
- MyBatis3를 사용하여 **데이터베이스에 액세스할 때 가장 중요한 역할**을 하는 구성 요소이다.

`Mapper interface`

- typeafe에서 매핑 파일에 정의된 SQL을 호출하는 인터페이스
- MyBatis3는 매퍼 인터페이스에 대한 구현 클래스를 자동으로 생성하므로 개발자는 인터페이스만 생성하면 된다.

`Mapping file`

- SQL 및 O/R 매핑 설정을 설명하는 XML 파일

### Database Access 하는 순서

![Untitled 2](https://user-images.githubusercontent.com/90807141/188309233-d5e2515b-57fa-403d-b023-dacc6af2ac2e.png)

![Untitled 3](https://user-images.githubusercontent.com/90807141/188309255-a71cbb9b-d4d9-4c91-a3e7-12378f46331e.png)

**[응용 프로그램 시작시 수행되는 프로세스]**

(1) 응용 프로그램이 SqlSessionFactoryBuilder를 위해 SqlSessionFactory를 빌드하도록 요청

(2) SqlSessionFactoryBuilder는 SqlSessionFactory를 생성하기 위한 MyBatis 구성 파일을 읽는다.

(3) SqlSessionFactoryBuilder는 MyBatis 구성 파일의 정의에 따라 SqlSessionFactory를 생성

<br>

**[클라이언트의 각 요청에 대해 수행되는 프로세스]**

(4) 클라이언트가 응용 프로그램에 대한 프로세스를 요청

(5) 응용 프로그램은 SqlSessionFactoryBuilder를 사용하여 빌드된 SqlSessionFactory에서 SqlSession을 가져온다.

(6) SqlSessionFactory는 SqlSession을 생성하고 이를 애플리케이션에 반환

(7) 응용 프로그램이 SqlSession에서 매퍼 인터페이스의 구현 개체를 가져온다.

(8) 응용 프로그램이 매퍼 인터페이스 메서드를 호출

(9) 매퍼 인터페이스의 구현 개체가 SqlSession 메서드를 호출하고 SQL 실행을 요청

(10) SqlSession은 매핑 파일에서 실행할 SQL을 가져와 SQL을 실행

---

## **MyBatis-Spring 컴포넌트 구조**

`org.mybatis.spring.SqlSessionFactoryBean`

- SqlSessionFactory를 작성하고 **Spring DI 컨테이너에 개체를 저장**하는 구성 요소.
- 표준 MyBatis3에서 SqlSessionFactory는 MyBatis 구성 파일에 정의된 정보를 기반으로 한다.
- 그러나 SqlSessionFactoryBean을 사용하면 **MyBatis 구성 파일이 없어도 SqlSessionFactory를 빌드**할 수 있다.

`org.mybatis.spring.mapper.MapperFactoryBean`

- **Singleton Mapper 개체를 만들고** **Spring DI 컨테이너에 개체를 저장**하는 구성 요소.
- MyBatis3 표준 메커니즘에 의해 생성된 매퍼 객체는 스레드가 안전하지 않다.

  따라서 각 스레드에 대한 인스턴스를 할당해야 했다.

- MyBatis-Spring 구성 요소에 의해 생성된 매퍼 개체는 안전한 매퍼 개체를 생성할 수 있다.

  따라서 서비스 등 싱글톤 구성요소에 DI를 적용할 수 있다.


`org.mybatis.spring.SqlSessionTemplate`

- SqlSession 인터페이스를 구현하는 Singleton 버전의 SqlSession 구성 요소.
- MyBatis3 표준 메커니즘에 의해 생성된 SqlSession 개체가 스레드에 안전하지 않다.

  따라서 각 스레드에 대한 인스턴스를 할당해야 했다.

- MyBatis-Spring 구성 요소에서 생성된 SqlSession 개체는 안전한 스레드 SqlSession 개체를 생성할 수 있다.

  따라서 서비스 등 싱글톤 구성요소에 DI를 적용할 수 있다.


### Database Access 하는 순서

![Untitled 4](https://user-images.githubusercontent.com/90807141/188309263-57e667bd-36c6-44d3-aa8c-a1bad69cf4f5.png)

**[응용 프로그램 시작시 수행되는 프로세스]**

(1) SqlSessionFactoryBean은 SqlSessionFactoryBuilder를 위해 SqlSessionFactory를 빌드하도록 요청

(2) SessionFactoryBuilder는 SqlSessionFactory 생성을 위해 MyBatis 구성 파일을 읽는다.

(3) SqlSessionFactoryBuilder는 MyBatis 구성 파일의 정의에 따라 SqlSessionFactory를 생성

-> 따라서 생성된 SqlSessionFactory는 Spring DI 컨테이너에 의해 저장

(4) MapperFactoryBean은 안전한 SqlSession(SqlSessionTemplate) 및 스레드 안전 매퍼 개체(Mapper 인터페이스의 프록시 객체)를 생성

-> 따라서 생성되는 매퍼 객체는 스프링 DI 컨테이너에 의해 저장되며 서비스 클래스 등에 DI가 적용

-> 매퍼 개체는 안전한 SqlSession(SqlSessionTemplate)을 사용하여 스레드 안전 구현을 제공

<br>

**[클라이언트의 각 요청에 대해 수행되는 프로세스]**

(5) 클라이언트가 응용 프로그램에 대한 프로세스를 요청

(6) 애플리케이션(서비스)은 DI 컨테이너에서 주입한 매퍼 개체(매퍼 인터페이스를 구현하는 프록시 개체)의 방법을 호출

(7) 매퍼 객체는 호출된 메소드에 해당하는 SqlSession (SqlSessionTemplate ) 메서드를 호출

(8) SqlSession (SqlSessionTemplate )은 프록시 사용 및 안전한 SqlSession 메서드를 호출

(9) 프록시 사용 및 스레드 안전 SqlSession은 트랜잭션에 할당된 MyBatis3 표준 SqlSession을 사용

트랜잭션에 할당된 SqlSession이 존재하지 않는 경우 SqlSessionFactory 메서드를 호출하여 표준 MyBatis3의 SqlSession을 가져온다.

(10) SqlSessionFactory는 MyBatis3 표준 SqlSession을 반환

반환된 MyBatis3 표준 SqlSession이 트랜잭션에 할당되기 때문에 동일한 트랜잭션 내에 있는 경우 새 SqlSession을 생성하지 않고 동일한 SqlSession을 사용.

on 메서드를 호출하고 SQL 실행을 요청

(11) MyBatis3 표준 SqlSession은 매핑 파일에서 실행할 SQL을 가져와 실행

출처

[https://khj93.tistory.com/entry/MyBatis-MyBatis란-개념-및-핵심-정리](https://khj93.tistory.com/entry/MyBatis-MyBatis%EB%9E%80-%EA%B0%9C%EB%85%90-%EB%B0%8F-%ED%95%B5%EC%8B%AC-%EC%A0%95%EB%A6%AC)
