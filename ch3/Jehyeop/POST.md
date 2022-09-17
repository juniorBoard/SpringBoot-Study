# POST
- 애플리케이션을 통해 데이터베이스 등의 저장소에 리소스를 저장할 때 사용
- API에서는 저장하고자 하는 리소스나 값을 *HTTP 바디(body)에 담아 서버에 전달.  
  → 눈에 보이지 않아서 보안에 좋다. , 로그인 같은 경우 (보안이 필요한 경우)  
- @RequestBody + Map or Dto

## @RequestMapping
```java
@RequestMapping(value = "/domain", method = RequestMethod.POST) //요청 형식을 POST로만 설정
public String postExample() {
    return "Hello Post API";
}
```

## @RequestBody + Map
- 리소스를 HTTP Body에 값을 넣어 전송 ( 일반적으로 JSON형식)
```java
//http://localhost:8080/api/v1/post-api/member
@PostMapping(value = "/member")
public String postMember(@RequestBody Map<String, Object> postData) {
    StringBuilder sb = new StringBuilder();

    postData.entrySet().forEach(map -> {
        sb.appen(map.getKey() + " : " + map.getValue() + "\n");
    });
        return sb.toString();
}
```

## @RequestBody + Dto

```java
//http://localhost:8080/api/v1/post-api/member2
@PostMapping(value = "/member2")
public String postMember(@RequestBody MemberDto memberDto) {
    return memberDto.toString();
}
```