# DELETE
- 서버를 거쳐 데이터베이스 등의 저장소에 있는 리소스를 삭제할때 사용
- @PathVariable , @RequestParam

## @PathVariable
```java
// http://localhost:8080/api/v1/delete-api/{String 값}
@DeleteMapping(value = "/{variable}")
public String DeleteVariable(@PathVariable String variable) {
    return variable;
}
```

## @RequestParam
```java
// http://localhost:8080/api/v1/delete-api/request1?email=value
@DeleteMapping(value = "/request1")
public String getRequestParam1(@RequestParam String email) {
    return "e-mail : " + email;
}
```