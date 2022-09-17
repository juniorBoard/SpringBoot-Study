# Spring Boot
**필요한 모듈을 추가하다보며 복잡해지는 문제를 해결하기 위해 틍장한 것이 스프링 부트이다.**

    공식 사이트 문구
    “스프링 부트를 이용하면 단독으로 실행 가능한 상용 수준의 스프링 기반 애플리케이션을 손쉽게 만들 수 있다.”

## 의존성 관리 (SpringFrameWork vs SpringBoot)
- **스프링 프레임 워크**
    - 개발에 필요한 각 모듈의 의존성을 직접 설정
    - 호환되는 버전을 명시해야 정상 동작
    - 애플리케이션에서 사용하는 스프링 프레임워크나 라이브러리의 버전을 올리는 상황에서는 연관된 다른 라이브러리의 버전까지 고려해야 한다.
- **스프링 부트**
    - ‘spring-boot-starter’라는 의존성을 제공 (종류가 여러개다)
    - 각 라이브러리의 기능과 관련해서 자주 사용되고 서로 호환되는 버전의 모듈 조합을 제공
- **많이 사용되는 ‘spring-boot-starter’ 라이브러리**
    - spring-boot-starter-web: 스프링 MVC를 사용하는 RESTful 애플리케이션을 만들기 위한 의존성, 기본으로 내장 톰캣(Tomcat)이 포함돼 있어 jar 형식으로 실행 가능
    - spring-boot-starter-test: JUnit Jupiter, Mockito 등의 테스트용 라이브러리를 포함
    - spring-boot-starter-jdbc: HikariCP 커넥션 풀을 활용한 JDBC 기능을 제공
    - spring-boot-starter-security: 스프링 시큐리티(인증, 권한, 인가 등) 기능을 제공
    - spring-boot-starter-data-jpa: 하이버네이트를 활용한 JPA 기능을 제공
    - spring-boot-starter-cache: 스프링 프레임워크의 캐시 기능을 지원

---

## WAS ( Web Application Server )

- DB 조회나 다양한 로직 처리를 요구하는 **동적인 컨텐츠를 제공**하기 위해 만들어진 Application Server
- HTTP를 통해 컴퓨터나 장치에 애플리케이션을 수행해주는 미들웨어(소프트웨어 엔진)이다.
- 웹컨테이너 혹은 서블릿 컨테이너라고도 불린다. (즉, WAS는 JSP, Servlet 구동 환경을 제공 )

**역할**

- WAS = Web Server + Web Container
- Web Server 기능들을 구조적으로 분리하여 처리하고자하는 목적
- 분산 트랜잭션, 보안, 메시징, 쓰레드 처리 등의 기능을 처리하는 분산 환경에서 사용된다.
- 주로 DB 서버와 같이 수행

**주요 기능**

- 프로그램 실행 환경과 DB 접속 기능 제공
- 여러개의 트랜잭션 관리 기능
- 업무를 처리하는 비즈니스 로직 수행

→ **Tomcat**, JBoss, **Jeus**, Web Sphere 등등

---

### **Web Server**

- **정적인 컨텐츠(html, css, js)를 제공**하는 서버
- 소프트웨어와 하드웨어로 구분
- 하드웨어 : Web 서버가 설치되어 있는 컴퓨터
- 소프트웨어 : 웹 브라우저 클라이언트로부터 HTTP 요청을 받아 정적인 컨텐츠를 제공하는 컴퓨터 프로그램

**기능**

- HTTP 프로토콜을 기반으로 하여 클라이언트(웹 브라우저 또는 웹 크롤러)의 요청을 서비스 하는 기능을 담당

→ **Apache Server**, Nginx, IIS(Windows 전용 Web 서버) 등등

**사용하는 이유**

- **WAS가 해야할 일의 부담을 줄이기 위해서 사용**
- WAS 앞에 웹서버를 둬서 **웹서버에서는 정적인 문서만 처리**하도록 하고,  
  WAS는 **애플리케이션의 로직만 수행**하도록 기능을 분배하여 서버의 부담을 줄이기 위함