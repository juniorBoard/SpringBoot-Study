# TDD
테스트 주도 개발 : 테스트를 먼저 설계 및 구축 후 테스트를 통과할 수 있는 코드를 짜는 것

<br>

TDD는 애자일 개발 방식 중 하나
- 코드 설계 시 원하는 단계적 목표에 대해 설정하여 진행하고자 하는 것에 대한 결정 방향의 갭을 줄이고자 한다.
- 최초 목표에 맞춘 테스트를 구축하여 그에 맞게 코드를 설계하기 때문에 보다 적은 의견 충돌을 기대할 수 있다.
  (방향 일치로 인한 피드백과 진행 방향의 충돌 방지)

<br>
<br>
<br>

## 테스트 코드 작성 목적
- 코드의 안정성을 높일 수 있다.
- 기능을 추가하거나 변경하는 과정에서 발생할 수 있는 Side-Effect를 줄일 수 있다.
    - ex) A기능을 수정했는데 갑자기 B기능이 실행이 안되는 상황
- 해당 코드가 작성된 목적을 명확하게 표현할 수 있다.
    - 코드에 불필요한 내용이 들어가는 것을 비교적 줄일 수 있다.

<br>
<br>
<br>

## JUnit 이란
Java 진영의 대표적인 Test Framework

단위 테스트(Unit Test)를 위한 도구를 제공
- 단위 테스트란?
    - 코드의 특정 모듈이 의도된 대로 동작하는지 테스트 하는 절차를 의미
    - 모든 함수와 메소드에 대한 각각의 테스트 케이스(Test Case)를 작성하는 것

어노테이션을 기반으로 테스트를 지원

단정문(Assert)으로 테스트 케이스의 기댓값에 대해 수행 결과를 확인할 수 있다.

*Spring Boot 2.2 버전 부터 JUnit5 버전을 사용한다.

<br>
<br>
<br>

### JUnit LifeCycle Annotation
JUnit5는 아래와 같은 테스트 라이프 사이클을 가지고 있다.
<br>

|Annotation|Description|
|:---:|:---:|
|@Test|테스트 용 메소드를 표현하는 어노테이션|
|@BeforeEach|각 테스트 메소드가 시작되기 전에 실행되어야 하는 메소드를 표현|
|@AfterEach|각 테스트 메소드가 시작된 후 실행되어야 하는 메소드를 표현|
|@@BeforeAll|테스트 시작 전에 실행되어야 하는 메소드를 표현(static 처리 필요)|
|@AfterAll|테스트 종료 후에 실행되어야 하는 메소드를 표현(static 처리 필요)|

<br>
<br>
<br>

### JUnit Main Annotation
**@SpringBootTest**
- 통합 테스트 용도로 사용된다.
- `@SpringBootApplication`을 찾아가 하위의 모든 Bean을 스캔하여 로드한다.
- 그 후 Test 용 Application Context를 만들어 Bean을 추가하고, MockBean을 찾아 교체

<br>

**@ExtendWith**
- Junit4에서 `@RunWith`로 사용되던 어노테이션이 **ExtendWith**로 변경된다.
- `@ExtendWith`는 메인으로 실행될 Class를 지정할 수 있다.
- `@SpringBootTest`는 기본적으로 @ExtendWith가 추가되어 있다.

<br>

**@WebMvcTest(Class명.class)**
- ()에 작성된 클래스만 실제로 로드하여 테스트를 진행한다.
- 웹 MVC와 관련해서 test 하기 때문에 컨트롤러와 관련된 test에서 많이 사용된다.
- 매개변수를 지정해주지 않으면 `@Controller`, `@RestController`, `@RestControllerAdvice` 등            
  컨드롤러와 연관된 Bean이 모두 로드된다.
- 스프링의 모든 Bean을 로드하는 @SpringBootTest 대신 컨트롤러 관련 코드만 테스할 경우 사용한다.

<br>

**@Autowired** (about Mockbean)
*Mockbean에서 사용될 때를 얘기한다.
- Controller의 API를 테스트하는 용도인 MockMvc 객체를 주입받는다.
- MocMvc의 객체를 주입받기 위해서 MockMvc 객체를 선언해주고 그 위에 `@Autowired`을 사용한다.
- `perform()` 메소드를 활용하여 컨트롤러의 동작을 확인할 수 있다. (http 통신을 도와주는 역할)             
  `.andExpect()`, `andDo()`, `andReturn()` 등의 메소드를 같이 활용한다.

<br>

**@MockBean**
- 테스트할 클래스에서 주입 받고 있는 객체에 대해 가짜 객체를 생성해주는 어노테이션
- 해당 객체는 실제 행위를 하지 않는다.
- `given()` 메소드를 활용하여 가짜 객체의 동작에 대해 정의하여 사용할 수 있다.

<br>

**@AutoConfigureMockMvc**
- spring.test.mockmvc의 설정을 로드하면서 MockMvC의 의존성을 자동으로 주입한다.
- MockMvc 클래스는 REST API 테스트를 할 수 있는 클래스를 얘기한다.

<br>

**@Import**
- 필요한 Class들을 Configuration으로 만들어 사용할 수 있다.
- Configuration Component 클래스도 의존성 설정할 수 있다.
- Import된 클래스는 `@Autowired`와 같은 객체를 주입받는 방식으로 사용할 수 있다.

<br>
<br>
<br>

### JUnit 통합 테스트
통합 테스트는 여러 기능을 조합하여 전체 비즈니스 로직이 제대로 동작하는지 확인하는 것을 의미한다.

통합 테스트의 경우 `@SpringBootTest`를 사용하여 진행한다.
- `@SpringBootTest`는 `@SpringBootApplication`을 찾아가서 모든 Bean을 로드하게 된다.
- 이 방법을 대규모 프로젝트에서 사용할 경우, 테스트를 실행할 때마다 모든 빈을 스캔하고 로드하는 작업이 반복되어                     
  매번 무거운 작업을 수행해야 한다.

<br>
<br>
<br>

### JUnit 단위 테스트
단위 테스트는 프로젝트에 필요한 모든 기능에 대한 테스트를 각각 진행하는 것을 의미한다.

일반적으로 스프링 부트에서는         
`org.springframework.boot:spring-boot-starter-test` 디펜던시만으로 의존성을 모두 가질 수 있다.

<br>

**F.I.R.S.T 원칙**
- Fast : 테스트 코드의 실행은 빠르게 진행되어야 한다.
- Independent : 독립적인 테스트가 가능해야한다.
- Repeatable : 테스트는 매번 같은 결과를 만들어야한다. (같은 input → 같은 output)
- Self-Validating : 테스트는 그 자체로 실행하여 결과를 확인할 수 있어야 한다.
- Timeply : 단위 테스트는 비즈니스 코드가 완성되기 전에 구성하고 테스트가 가능해야 한다.                
  → 코드가 완성되기 전부터 테스트가 따라와야한다는 TDD의 원칙을 담고 있다.


<br>
<br>
<br>

###### 출처
[테스트 코드 적용하기 (JUnit, TDD) [ 스프링 부트 (Spring Boot) ]](https://www.youtube.com/watch?v=SFVWo0Z5Ppo)