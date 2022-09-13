# 테스트코드 동작 목표

## 노래 목록을 보여준다.
1. Controller : GET POST PUT DELETE
2. Service : 노래 목록 가져오기, 노래 정보 저장, 노래 정보 수정, 노래 정보 삭제
3. Repository : DB에 잘 반영되는지 확인

## 테스트 코드 작성 순서
1. Controller -> Service -> Repository
2. 노래 정보 저장 예상 동작 작성
3. 노래 정보 저장이 잘 동작하게 구현
4. 좀 더 나은 방법으로 리팩토링
    - indent 2 를 넘지 않게 작성한다.
      - 메소드를 분리 하면 된다. - if문 안에 for문이 있으면 for문은 밖에 새로운 메소드로 작성
    - else 예약어를 쓰지 않는다.
      - if문 밖으로 나오면 바로 return을 해준다.
    - 로컬변수는 최대한 줄여 불필요한 코드를 줄여준다.
    - 메소드가 한가지 일만 하도록 구현한다.

## 테스트 과정

### 기본 세팅
- @Autowired 로 MockMvc 주입
- DI 관게의 클래스를 @MockBean 으로 주입  
  ex) MusicService, MusicRepository ...
- 각각 테스트코드에 @Test 어노테이션을 붙여준다.
- 성향에 따라 @DiSplayName("이름, 간단한 설명") 설정 가능
- given, when, then 구성으로 작성

---

### Controller Test Code 
- @WebMvcTest({테스트할 컨트롤러 이름}.class) 어노테이션을 붙여준다.  
  - Controller 한정

#### GET
1. given 
   - Service의 역할(요청값과 기댓값)을 작성한다.
   - Parameters 값 작성한다.
2. when 
   - 기본 세팅에서 주입한 mockMvc 를 사용해 경로와 key 값들을 조회한다.
3. then 
   - verify 로 테스트중인 메소드가 정상 동작했는지 검증한다.
4. 오류를 없에 가면서 코드를 구현한다.

#### POST
1. given 
   - Service의 역할(요청값과 기댓값)을 작성한다.
     - 저장할 DTO 값을 설정한다. ( 변경: 저장할 객체 클래스 설정 )
     - return 받을 예상 ResponseDto 값을 작성한다.
   - 저장할 DTO 값을 json 형태로 변경
2. when
   - mockMvc 를 사용 경로와 key 값들을 조회한다.
3. then
   - verify 로 Service의 역할이 잘 동작했는지 검증한다.
4. 오류를 없에 가면서 코드를 구현한다.

#### PUT
1. given 
   - 기본값을 설정한다. (Repository에 저장)
   - Service의 역할(요청값과 기댓값)을 작성한다.
   - 변경할 DTO 값을 json 형태로 변경
2. when
   - mockMvc 를 사용 경로와 key 값들을 조회한다.
3. then
   - verify 로 Service의 역할이 잘 동작했는지 검증한다.
4. 오류를 없에 가면서 코드를 구현한다.

#### DELETE
1. given
   - 기본값을 설정한다.(Repository에 저장)
   - Service의 역할(요청값과 기댓값)을 작성한다.
2. when
   - mockMvc 를 사용하여 경로를 설정한다.
3. then
   - verify 로 Service의 역할이 잘 동작했는지 검증한다.
   - return 값이 없으므로 임의로 String 값을 넣어줄 수도 있다.
4. 오류를 없에 가면서 코드를 구현한다.

---

### Service Test Code

#### GET
1. given
   - 기댓값을 설정한다. 
   - 저장소의 역할(요청값과 기댓값)을 작성한다.
2. when
   - 구현하고자 하는 메소드를 작성한다.
3. then
   - 메소드 동작 후 값이 맞는지 Assertions로 검증
   - 저장소의 역할이 잘 동작했는지 확인.
4. 오류를 없에 가면서 코드를 구현한다.

#### POST
1. given
   - 저장소의 역할(요청값과 기댓값)을 작성한다.
2. when
   - 구현하고자 하는 메소드를 작성한다.
3. then
   - 메소드 동작 후 값이 맞는지 Assertions로 검증
   - 저장소의 역할이 잘 동작했는지 확인.
4. 오류를 없에 가면서 코드를 구현한다.

#### PUT
1. given
   - 기본값 설정 및 저장 (수정전 값 설정)
   - 기대값 설정 (수정 후 값 설정)
   - 저장소 역할 설정(find, save)
2. when
   - 구현하고자 하는 메소드를 작성한다.
3. then
   - 메소드 동작 후 값이 맞는지 Assertions로 검증
   - 저장소의 역할이 잘 동작했는지 확인.
4. 오류를 없에 가면서 코드를 구현한다.

#### DELETE
1. given
   - 기본값 설정 및 저장 (삭제전 값 설정)
2. when
   - 구현하고자 하는 메소드 작성
3. then
   - 메소드 동작 후 값이 맞는지 Assertions로 검증

---

## 테스트 중 에러

1. MockMvc Autowired 안되는 오류
    - IntelliJ 2.7.0 버전 이후에서 발생 ( 그냥 돌려도 문제는 없다고 한다.)

2. 메모리 주소값 불일치 오류
   - 테스트코드에서 사용하는 new MusicDto(~~) 와 구현 코드의 postMapping의 @RequestBody MusicDto 객체의
     메모리 주소값이 달라 요청한 값을 다른 Dto에 담고 못 읽어오는 문제 입니다.  

   해결방법
   - Mockito ArgumentMatchers 의 any() 메소드를 사용
   - MusicDto 타입으로 설정 