# SpringBoot

## 의존성 관리

-   스프링 프레임워크나 라이브러리의 버전을 변경하려면 관련된 다른 라이브러리의 호환성까지 확인해야함.

-   스프링 부트에서는 spring-boot-start-xxx 들로 자주 사용되고 서로 호환되는 버전의 모듈 조합을 제공.

<br>

## 자동 설정

-   _@ComponentScan_ 과 _@EnableAutoConfiguration_ 어노테이션으로 _@Component_ 시리즈 어노테이션이 붙은 클래스를 발견해 빈(Bean)을 등록함.

-   _@EnableAutoConfiguration_ 어노테이션을 통해 다양한 자동 설정이 일부 조건을 거쳐 적용.

<br>

## 내장 WAS (Web Application Server)

-   스프링 부트는 내장 WAS가 존재.
-   spring-boot-starter-web의 경우 톰캣을 내장.
-   스프링 부트의 자동 설정 기능이 톰캣에도 적용되어 특별한 설정 없이도 톰캣을 실행할 수 있다.
