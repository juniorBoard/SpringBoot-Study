# RestController
RestController는 Contrller에 ResponseBody가 추가된 것이다.  
주 용도는 Json 형태로 객체 데이터를 반환하는 것이다.

### [동작 방식]
1. Client는 URI 형식으로 웹 서비스에 요청을 보낸다.
2. DispatcherServlet이 요청을 위임할 HandlerMapping을 찾는다.
3. HandlerMapping을 통해 요청을 Controller로 위임한다.
4. Controller는 요청을 처리한 후에 객체를 반환한다.
5. 반환되는 객체는 *Json으로 Serialize되어 사용자에게 반환된다.

### [예제 코드]
```java
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping(value = "/users")
    public User findUser(@RequestParam("userName") String userName){
        return userService.findUser(user);
    }

    @GetMapping(value = "/users")
    public ResponseEntity<User> findUserWithResponseEntity(@RequestParam("userName") String userName){
        return ResponseEntity.ok(userService.findUser(user));
    }
}

```