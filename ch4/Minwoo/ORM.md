# ORM
>ORM (Object Relational Mapping) : 객체 관계 매핑

객체(자바 클래스)와 데이터베이스의 테이블 사이의 차이와 제약사항을 해결하는 역할.

## 장점
+ 데이터베이스 쿼리를 객체지향적으로 조작할 수 있다.
  + 직접 쿼리문을 작성하지 않아도 되고, 코드 가독성도 높아진다.
+ 데이터베이스에 대한 종속성이 줄어든다.
  + DB 교체 시 어떤 DB을 사용하는지 설정만 변경해준다면 알아서 동작한다.



## 단점
+ 서비스 구현에 한계가 있다.
  + 데이터량이 많은 경우 데이터 조회나 처리 시 속도 저하 등의 성능 문제가 발생할 수 있다.
  + 빠른 속도를 위해 직접 쿼리문을 작성해야 하는 경우도 있다.



+ 객체와 데이터베이스 관점의 불일치가 발생한다.
  + 세분성(Granularity)
  + 상속성(Inheritance)
  + 식별성(Identity)
  + 연관성(Associations)