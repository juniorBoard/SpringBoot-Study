# Controller

## **[Controller 로 View 반환하기]**  
Spring MVC의 컨트롤러인 Controller 는 주로 View를 반환하기 위해 사용한다.
Spring MVC Controller 는 Client의 요청으로부터 View를 반환한다.

### [동작 방식]
1. Client는 **URI** 형식으로 웹 서비스에 요청을 보낸다.
2. DispatcherServlet이 요청을 위임할 HandlerMapping을 찾는다.
3. HandlerMapping을 통해 요청을 Controller로 위임한다.
4. Controller는 요청을 처리한 후에 ViewName을 반환한다.
5. DispatcherServlet은 ViewResolver를 통해 ViewName에 해당하는 View를 찾아 사용자에게 반환한다.

## **[Contrller로 Data 반환하기]**  
데이터를 반환하기 위해서는 @ResponseBody 어노테이션을 활용해주어야 한다. 
이렇게 하면 Controller도 Json 형태로 데이터를 반환할 수 있다.

### [동작 방식]
1. Client는 **URI** 형식으로 웹 서비스에 요청을 보낸다.
2. DispatcherServlet이 요청을 위임할 HandlerMapping을 찾는다.
3. HandlerMapping을 통해 요청을 Controller로 위임한다.
4. Controller는 요청을 처리한 후에 객체를 반환한다.
5. 반환되는 객체는 Json으로 Serialize되어 사용자에게 반환된다.

### [예제 코드]
```java
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/users")
    public @ResponseBody ResponseEntity<User> findUser(@RequestParam("userName") String userName){
        return ResponseEntity.ok(userService.findUser(user));
    }

    @GetMapping(value = "/users/detailView")
    public String detailView(Model model, @RequestParam("userName") String userName){
        User user = userService.findUser(userName);
        model.addAttribute("user", user);
        return "/users/detailView";
    }
}
```
→ User를 json으로 반환하기 위해 @ResponseBody라는 어노테이션을 붙여주고 있다.