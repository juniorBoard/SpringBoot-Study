# JWT (JSON Web Token)

1. **헤더(Header)**
    - 검증과 관련된 내용(alg, typ)을 담는 부분

        ```java
        {
        	"alg": "HS256", //alg 속성 : 해싱 알고리즘
        	"typ": "JWT" //typ 속성 : 토큰의 타입을 지정
        }
        ```

      → 이렇게 완성된 해더는 Base64Url 형식으로 인코딩된다.

2. **내용(Payload)**
    - 토큰에 담는 정보를 포함, 이곳에 포함된 속성들을 클레임(Claim)이라 하며 3가지로 분류된다.

      **Claim**

        - 등록된 클레임(Registered Claims)
            - iss : JWT의 발급자(Issuer) 주체를 나타낸다.
            - sub : JWT의 제목(Subject)
            - aud : JWT의 수신인(Audience) → 요청을 처리하는 주체가 aud값으로 자신을 식별하지 않으면 JWT는 거부된다.
            - exp : JWT의 만료시간(Expiration)
            - nbf : ‘Not Before’을 의미
            - iat : JWT가 발급된 시간(Issued at)
            - jti : JWT의 식별자(JWT ID) → 주로 중복처리방지에 사용
        - 공개된 클레임(Public Claims)
        - 비공개 클레임(Private Claims)

        ```java
        {
        	"sub": "wikibooks payload",
        	"exp": "1602076408",
        	"userId": "wikibooks",
        	"username": "flature"
        }
        ```

3. **서명(Signature)**
    - 인코딩된 헤더, 인코딩된 내용, 비밀키, 헤더의 알고리즘 속성값을 가져와 생성
    - 토큰의 값들을 포함해서 암호화

        ```java
        HMACSHA256(
        	base64UrlEncode(header)+ "." +
        	base64UrlEncode(payload),
        	secret
        )
        ```