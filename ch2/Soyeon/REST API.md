#### REST란?
자원(resource)의 표현(representation)에 의한 상태 전달

자원을 이름(자원의 표현)으로 구분하여 해당 자원의 상태(정보)를 주고 받는 모든 것을 의미한다.

<br><br><br>

#### REST API란?

**API란?**                 
데이터와 기능의 집합을 제공하여 컴퓨터 프로그램간 상호작용을 촉진하며, 서로 정보를 교환가능 하도록 하는 것                


**REST API**                
REST 기반으로 서비스 API를 구현한 것                

<br><br><br>

#### REST의 특징
- 서버-클라이언트 구조                
    - 자원이 있는 쪽이 Server, 자원을 요청하는 쪽이 Client                
- 무상태(Stateless)                

<br><br><br>

#### REST의 URI 설계 규칙
resource에 동사보다는 명사를 사용한다.                

resource에 대문자 보다는 소문자를 사용한다.                

URI에 HTTP Method가 들어가면 안된다.

URI에 행위에 대한 동사 표현이 들어가면 안된다. (CRUD 기능을 나타내는 것은 URI에 사용하지 않는다.)                
ex. `/members/show/1` → `/members/1`

<br><br><br>

출처                 
[[Network] REST란? REST API란? RESTful이란?](https://gmlwjd9405.github.io/2018/09/21/rest-and-restful.html)