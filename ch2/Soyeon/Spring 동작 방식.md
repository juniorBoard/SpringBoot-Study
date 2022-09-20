## Spring의 전체적인 실행 순서
Request → **DispatcherServlet** → **HandlerMapping**

→ (Controller → Service → DAO → DB → DAO → Service → Controller)

→ **DispatcherServlet** → **ViewResolver** → **View** → **DispatcherServlet** → Response

<br>

![image](https://user-images.githubusercontent.com/74857364/150326371-b191c328-f337-435d-a817-5745f64d1e9a.png)

<br>

1. 클라이언트가 Request 요청을 하면, **DispatcherServlet이** 요청을 가로챈다.

이 때, **DispatcherServlet**이 모든 요청을 가로채는 건 아니고 web.xml(설정 파일)에 등록된 내용만 가로챈다.    
(Spring Boot에서는 @**SpringBootApplication** 이라는 어노테이션으로 web.xml 파일을 대체 할 수 있다. )

2. **DispatcherServlet**이 가로챈 요청을 **HandlerMapping**에게 보내 해당 요청을 처리할 수 있는 Controller를 찾는다.

3. 실제 로직 처리 (Controller -> Service -> DAO -> DB -> DAO -> Service -> Controller)

4. 로직 처리 후 **ViewResolver**를 통해 view 화면을 찾는다.

5. 찾은 view 화면을 View에 보내면 이 결과를 다시 **DispatcherServlet**에 보내고, DispatcherServlet는 최종 클라이언트에게 전송한다.

<br>
</br>

***DispatcherServlet*** :           
HTTP 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 적합한 컨트롤러에 위임해주는 프론트 컨트롤러(Front Controller)

***HandlerMapping*** :            
HTTP 요청정보를 이용해서 컨트롤러를 찾아주는 기능을 수행

***HandlerAdapter*** :        
HandlerMapping을 통해 찾은 컨트롤러를 직접 실행하는 기능을 수행

***Controller*** :           
클라이언트의 요청을 처리한 뒤, 결과를 DispatcherServlet에게 리턴

***ModelAndView*** :            
컨트롤러가 처리한 결과 정보 및 뷰 선택에 필요한 정보를 담음

***ViewResolver*** :                
컨트롤러의 처리 결과를 생성할 뷰를 결정

<br>
</br>

`@Controller` : 어노테이션을 붙이면 servlet-context.xml에서 이것을 인식하여 컨트롤러로 등록한다.


`@RequestMapping` : 스프링은 HandlerMppaing에 의해 컨트롤러가 결정된다.

이 컨트롤러에서 HandlerAdapter에 의해 실행 메서드가 결정되는 데 @RequestMapping 어노테이션이 그 정보를 제공해 준다.

value에 해당하는 url이 GET 방식으로 요청이 들어올 때 해당 메서드를 실행한다.

<br>

[Spring MVC 프로젝트의 기본 구조와 실행 순서](https://devpad.tistory.com/24)

<br>