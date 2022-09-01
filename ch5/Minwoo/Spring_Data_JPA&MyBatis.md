# Spring Data JPA와 MyBatis 차이

## Spring Data JPA

Repository인터페이스

+ 인터페이스를 작성하면 동적으로 메서드를 생성.
+ 기본적으로 쿼리문 작성이 필수적이지 않음.
+ 직접쿼리문을 작성하여 사용도 가능.
+ ORM을 사용하여 DB에 종속적이지 않음. -> DB교체시 유연하게 대처 가능함.

## MyBatis

DAO인터페이스, Mapper.xml
+ 쿼리문을 직접 작성하는 Mapper.xml 필요.
+ ORM을 사용하지 않아 DB 교체시 쿼리문 수정이 필요해질 수 있음.