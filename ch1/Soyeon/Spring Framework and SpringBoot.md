### 자동 설정
- AutoConfig로 복잡한 설정 자동화

  기존 Spring에서는 Maven이나 Gradle을 통해 라이브러리의 의존성을 관리했다.                  
                 
  SpringBoot를 사용하면 부트 버전에 해당하는 스프링 라이브러리 뿐만 아니라 의존 관계에 있는 서드파티 라이브러리들도 호환되는 버전으로 다운로드해준다.                 
  (라이브러리의 버전 관리)                 
                 
    - @EnableAutoConfiguration
      <br>
      <br>
      <br>

### 내장 WAS
- 내장 Tomcat 및 테스트 환경                   
                           
  SpringBoot 내에 Tomcat 서버(WAS)를 내장하고 있기 때문에, 별도의 설정이 필요하지 않으며 빠르게 어플리케이션 실행이 가능하다.      

  JUnit 등의 테스트 관련 라이브러리도 내장하고 있기 때문에 쉬운 테스트도 가능하다.             

- 독립적으로 실행 가능한 Jar               
                
  개발 후 어플리케이션 배포를 위해서, 웹 프로젝트는 보통 WAR 파일로 패키징을 해야한다.                    
                 
  SpringBoot는 JAR 파일로 패키징해서 사용이 가능하다.
               
- Tomcat, jetty(경량 WAS), undertow(대규모 트래픽으로부터 Tomcat보다 안정적) 모두 가능                        

<br>
<br>
<br>

### 모니터링
**Actuator**

Spring Boot 의 모듈 중 하나인 "Actuator" 는 애플리케이션의 관리 및 모니터링을 지원해준다.                   

Actuator 는 사용 서비스 수준에서 필요로 할 모니터링 기능을 엔드포인트로 미리 만들어서 제공해준다.

대표적인 end point 들은 다음과 같다.

- `/health` : 애플리케이션의 상태정보

- `/metircs/{name}` : 애플리케이션의 metric 정보(system.cpu.usage, process files.open 등)

- `/beans` : 어떤 빈이 등록되어 있는지

- `/loggers/{name}` : 애플리케이션의 로거 구성

하지만 actuator 가 서버 관리자 말고 다른 사용자들도 보면 안된다. 

따라서 운영시에는 Spring Security 등을 이용하여 보안에 신경써줘야한다.

또한 actuator의 데이터를 영구 저장소에 저장해주지 않으면 없어진다. 

actuator의 정보는 메모리에 저장되기 때문에 tomcat 을 재시작하면 데이터가 날라간다. 

영구적으로 저장하기 위해선 별도의 logging 이나 DB가 필요하다.

<br>
<br>
<br>

### 정리

Spring Boot 는 약간의 설정만으로 Spring 애플리케이션을 만들 수 있게 해준다.

Spring Boot 는 Spring 프로젝트 중 하나로 Spring Framework 를 쉽게 사용하게 해주는 도구이지,               
Spring Framework 와 별개로 사용할 수 있는 것이 아니다.

<br>

출처                                       
[Spring에 비해 SpringBoot 가 가지는 특징](https://algopoolja.tistory.com/113)                                             
[Spring Boot 내장 WAS 종류와 특징](https://gofnrk.tistory.com/73)                  
[Spring 과 SpringBoot의 차이](https://java-man.tistory.com/4)                  