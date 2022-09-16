# 매개변수
Get 메서드와 Delete 메서드의 경우 일반적으로 URL에 데이터를 담는다.

## _@PathVariable_
URL 경로 자체에 담겨진 데이터

ex 1)
```java
@RestController
@RequestMapping("/api")
public class MyController {
	// http://localhost:8080/api/hello/menuhwang
    @GetMapping(value = "/hello/{name}")
    public String hello(@PathVariable String name) {
        return "Hello, " + name; // Hello, menuhwang
    }
}
```

ex2)
```java
@RestController
@RequestMapping("/api")
public class MyController {
	// http://localhost:8080/api/hello/menuhwang
    @GetMapping(value = "/hello/{name}")
    public String hello(@PathVariable("name") String userName) {
        return "Hello, " + userName; // Hello, menuhwang
    }
}
```

## _@RequestParam_
쿼리 형식으로 담겨진 데이터
> 예시 : http://www.sample.com/test?key=value&mode=test


```java
@RestController
@RequestMapping("/api")
public class MyController {
	// http://localhost:8080/api/hello?name=menuhwang&age=5
    @GetMapping(value = "/hello")
    public String hello(@RequestParam("name") String name, @RequestParam("age") int age) {
        return "Hello, " + name + ", " + age; // Hello, menuhwang, 5
    }
}
```