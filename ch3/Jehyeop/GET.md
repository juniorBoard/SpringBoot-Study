# GET
- 애플리케이션 서버(데이터 베이스)에서 값을 가져올때 사용
- @PathVariable, @RequestParam

## @RequestMapping
- 요청 *URL을 어떤 메서드가 처리할지 여부를 결정하는 것
```java
@RestController
@RequestMapping("/api/v1/get-api")
public class GetCrontroller {
    //http://localhost:8080/api/v1/get-api/hello
    @RequestMapping(value = "/hello", method = RequestMethod.GET) //요청 형식을 GET으로만 설정
    public String getHello() {
        return "Hello World";
    }
}
```
## 매개변수가 없는 GET 메서드

```java
// http://localhost:8080/api/v1/get-api/name
@GetMapping(value = "/name")
public String getName() {
    return "Flature";
}
```
  
## @PathVariable
```java
// http://localhost:8080/api/v1/get-api/variable1/{String 값}
@GetMapping(value = "/variable1/{variable}")
public String getVariable(@PathVariable String variable) {
    return variable;
}
```
```java
// http://localhost:8080/api/v1/get-api/variable1/{String 값}
@GetMapping(value = "/variable1/{variable}")
public String getVariable(@PathVariable("variable") String var) {
    return var;
}
```

### 동작 설명

1. /variable1/`{String 값}` 요청이 들어오면 `{String 값}` 에 맞게 `getVariable()` 메소드 동작
2. `@PathVariable` : URI 경로에 변수를 넣어준다.
3. `{String 값}`을 넣어주지 않으면 오류가 발생한다. (NPE 발생)

**해결법**  
- URL에 `"/variable1”` (`{String 값}`이 없는 경로)을 추가  
- @PathVariable(required = false) 설정 후 **if 처리** 또는 ***Optional 이용**

**규칙)**
1. @GetMapping 어노테이션의 값으로 URI를 입력할 때 중괄호를 사용해 **어느 위치에서 값을 받을지** 지정해야한다.
2. 메서드의 매개변수와 그 값을 연결하기 위해 @PathVariable을 명시 해야한다.
3. @GetMapping 어노테이션과 @PathVariable에 지정된 변수의 이름을 동일하게 맞춰야 한다.

## @RequestParam

```java
// http:localhost:8080/api/v1/get-api/request1?name=value1&email=value2&organization=value3
@GetMappint(value = "/request1")
public String getRequestParam1(
    @RequestParam("name") String name,
    @RequestParam String email,
    @RequestParam String organization) {
    return name + " " + email + " " + oranization;
}
```
**[출력]**  
    value1 value2 value3

## @RequestParam Map 활용
```java
// http:localhost:8080/api/v1/get-api/request2?key1=value1&key2=value2
@GetMappint(value = "/request2")
public String getRequestParam2(@RequestParam Map<String, String> param) {
    StringBuilder sb = new StringBuilder();
    param.entrySet().forEach(map -> {
	    sb.append(map.getKey() + " : " + map.getValue() + "\n");
    });
		
    return sb.toString();
}
```
→ 매개변수의 값이 일정하지 않을 경우 사용  

[**출력**]  
key1 : value1  
key2 : value2

## DTO 객체를 활용
```java
// http:localhost:8080/api/v1/get-api/request3?name=value1&email=value2&organization=value3
@GetMappint(value = "/request3")
public String getRequestParam1(MemberDto memberDto) {
    // return memberDto.getName() + " " + memberDto.getEmail() + " " + memberDto.getOranization();
    return memberDto.toString();
}
```

[**출력**]  
MemberDto에서 설정한 toString() 으로 출력 → @toString으로 생략가능  
ex) MemberDto(name=’value1’ , email=’value2’ , organization=’value3’}