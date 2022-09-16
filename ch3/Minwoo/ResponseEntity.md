# ResponseEntity

헤더와 Body로 구성된 HTTP 요청과 응답을 구성하는 HttpEntity.

ResponseEntity는 이 HttpEntity를 상속받아 구현.

```java
@RestController
@RequestMapping("/api")
public class MyController {
    @PostMapping(value = "/user")
    public ResponseEntity<MyDTO> postUser(@RequestBody MyDTO myDTO) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(myDTO);
    }
}
```