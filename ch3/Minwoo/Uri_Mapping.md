# URI Mapping

## _@RequestMapping_

### 메서드에 적용
```java
@RestController
public class MyController {
    // http://localhost:8080/api/hello
    @RequestMapping("/api/hello")
    public String hello() {
        return "Hello";
    }
}
```

<br>

### 클래스에 적용
```java
@RestController
@RequestMapping("/api")
public class MyController {
	// http://localhost:8080/api/hello
    @RequestMapping("/hello")
    public String hello() {
        return "Hello";
    }
}
```
클래스에 _@ReqeustMapping_ 어노테이션을 붙이면 해당 클래스 이하 메서드에 공통적으로 적용.

<br>

### 메서드 설정
```java
@RestController
@RequestMapping("/api")
public class MyController {
	// http://localhost:8080/api/hello, only GET Method
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello";
    }
}
```

**스프링 4.3 부터는 _@GetMapping_, _@PostMapping_, _@PutMapping_, _@DeleteMapping_ 어노테이션들로 HTTP 메서드 매핑을 한 번에 설정해 줄 수 있다.**