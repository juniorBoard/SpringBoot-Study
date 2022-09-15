# _@RequestBody_

Post 메서드와 Put 메서드는 Get과 Delete 메서드와 달리 URL에 값을 담아 보내지 않고 HTTP Body에 담아 보냄.

```java
@RestController
@RequestMapping("/api")
public class MyController {
	@PostMapping(value = "/user")
    public MyDTO postUser(@RequestBody MyDTO myDTO) {
        return myDTO;
    }
}
```

> DTO와 같이 객체를 리턴해주는 경우 getter가 없다면 값에 접근하지 못한다.
> _HttpMediaTypeNotAcceptableException_ 발생