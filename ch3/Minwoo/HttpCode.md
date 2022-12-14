# HTTP Code

## 2xx
성공적으로 요청이 처리되었음을 의미.

+ 200 [OK] : 처리 완료.
+ 201 [Created] : 생성 완료.
+ 204 [No Content] : 성공적으로 처리했지만 컨텐츠 제공이 없을 때.

## 3xx
리다이렉션이 이루어져야 한다는 의미.

+ 301 [Moved Permanently] : 영구적으로 컨텐츠가 이동했을 때.
+ 302 [Found] : 일시적으로 컨텐츠가 이동했을때.
+ 304 [Not Modified] : 요청된 리소스를 재전송할 필요가 없음. 캐시된 자원 사용.

## 4xx
요청이 올바르지 않음을 의미.

+ 400 [Bad Request] : 요청 자체가 잘못되었을 때 사용.
+ 401 [Unauthorized] : '인증' 없이 접근할 경우 발생.
+ 403 [Forbidden] :서버가 요청을 거부할 때 발생. 권한(인가)이 없을 때.
+ 404 [Not Found] : 찾는 리소스가 없을 때.
+ 405 [Method Not Allowed] : 서버에서 허용되지 않은 메소드로 요청시 사용.
+ 415 [Unsupported Media Type] : 사용자가 요청한 미디어타입이 서버에서 지원하지 않는 타입일 때.
+ 429 [Too Many Requests] : 일정 시간 동안 너무 많은 요청을 보냈을 때.

## 5xx
서버 오류.

**클라이언트가 500번 대 응답을 받지 않도록 상황에 맞게 400번 대 응답으로 처리할 것.**