# DataBase

**테이블 자동 생성 application.properties 설정**

`spring.jpa.hibernate.ddl-auto= create`

`#spring.jpa.show-sql=true`

**데이터 베이스 자동 조작 옵션**

- create : 기존 테이블을 지우고 새로 생성
- create-drop : 애플리케이션을 종료하는 시점에 테이블을 지운다.
- update : 실행될 객체를 검사해서 변경된 스키마를 갱신, 기존에 저장된 데이터는 유지
- validate : update처럼 객체를 검사하지만 스키마는 건들지 않는다. (*데이터베이스의 테이블 정보와 객체정보가 다른면 에러 발생)*
- none : ddl-auto 기능을 사용하지 않는다.

→ 운영 환경: create, create-drop, update (X)  ||  validate, none (O)

→ 개발 환경: create, update (O)