# 데이터 접근 계층 구조

## MyBatis 3.0 이전

+ DAO인터페이스 및 구현체

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;**SqlSession을 주입**받아 Mapper에 작성된 쿼리문 호출.

+ Mapper

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;쿼리문 작성.


## MyBatis 3.0 이후

### 1. DAO인터페이스와 Mapper

+ DAO인터페이스

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mapper에 작성된 쿼리문 호출.

+ Mapper.xml

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;쿼리문 작성.

### 2. DAO와 Mapper 통합

+ Mapper인터페이스

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;@Insert @Select 등의 어노테이션으로 인터페이스 내부에 쿼리문 작성.