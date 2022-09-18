# EntityManager
1. Transaction 단위마다 EntityManager 생성.
2. EntityManager가 생성되면 영속성 컨텍스트 생성.
3. 한 Transaction 안에서는 같은 영속성 컨텍스트 사용.

## EntityManager
+ 엔티티 매니저는 엔티티를 관리하는 객체.
+ 영속성 컨텍스트에 등록하거나 삭제 혹은 엔티티 상태를 DB에 저장하는 역할.

## Entity 생명주기
+ New : 영속성 컨텍스트에 추가되지 않은 엔티티 객체의 상태
+ Managed : 영속성 컨텍스트에 의해 엔티티 객체가 관리되는 상태
+ Detached : 영속성 컨텍스트에 의해 관리되던 엔티티 객체가 컨텍스트와 분리된 상태
+ Removed : 데이터베이스에서 레코드를 삭제하기 위해 영속성 컨텍스트에 삭제 요청을 한 상태.

## 영속성 컨텍스트
+ 영속성 컨텍스트는 객체를 보관하는 기능을 수행한다.

<br>

![Persistance_Context_Diagram](https://user-images.githubusercontent.com/77231274/190912646-6922236f-c11f-4adf-b547-79ab70e88a70.png)
