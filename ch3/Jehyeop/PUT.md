#  PUT
- 저장소에 존재하는 리소스 값을 업데이트 하는데 사용
- @RequestBody + Map or Dto (ResponseEntity)

## @RequestBody + Map
- @RestController 어노테이션이 지정된 클래스면 생략 가능
```java
//http://localhost:8080/api/v1/put-api/member
@PutMapping(value = "/member")
public String postMamber(@RequestBody Map<String, Object> putData) {

    StringBuilder sb = new StringBuilder();

    postData.entrySet().forEach(map -> {
        sb.appen(map.getKey() + " : " + map.getValue() + "\n");
    });

    return sb.toString();
}
```
## @RequestBody + Dto

```java
//http://localhost:8080/api/v1/put-api/member1
@PutMapping(value = "/member1")
public String postMamber(@RequestBody MemberDto memberDto) {
    return memberDto.toString();
}
```

[출력]  
memberDto 의 `toString` 메소드 결괏값 출력

---

```java
//http://localhost:8080/api/v1/put-api/member2
@PutMapping(value = "/member2")
public String postMamber(@RequestBody MemberDto memberDto) {
    return memberDto;
}
```

[출력]

```html
{
    name : “Flature” ,
    email : “aaaa@gmail.com” ,
    organization : “Around Hub Studio
} 
```

## ResponseEntity
- 헤더(Header)와 Body로 구성된 HTTP 요청과 응답을 구성하는 역할 수행
- 서버에 들어온 요청에 대한 응답 데이터를 구성해서 전달
- HttpEntity로 부터 HttpHeaders와 Body를 가지고 자체적으로 HttpStatus를 구현

```java
@PutMapping(value = "/member3")
public ResponseEntity<MemberDto> postMemberDto3(@RequestBody MemberDto memberDto) {
    return ResponseEntity
        .status(HttpStatus.ACCEPTED)
        .body(memberDto);
}
```
**HttpStatus.ACCEPTED : 응답코드 202를 갖는다.**  
→ 요청이 성공적으로 접수되었으나, 아직 해당 요청에 대해 처리 중이거나 처리 시작 전임을 의미  
→ HTTP가 나중에 요청 처리 결과를 나타내는 비동기 응답을 보낼 방법이 없다는 것을 의미  
→ 다른 프로세스나 서버가 요청을 처리하는 경우 또는 일괄 처리를 위한 것입니다.

