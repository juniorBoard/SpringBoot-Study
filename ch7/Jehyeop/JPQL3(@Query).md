## @Query 어노테이션 사용법

: 직접 JPQL을 작성하는 방법

단점

- 직접 문자열을 입력하기 때문에 컴파일 시점에 에러를 잡지 못하고 런타임 에러가 발생할 수 있다.
- 쿼리의 문자열이 잘못된 경우에는 애플리케이션이 실행된 후 로직이 실행되고 나서야 오류를 발견할수 있다.
- 개발환경에서는 문제가 없지만 배포에하고 나서 오류가 발견되는 리스크가 있다.

### **@Query 어노테이션을 사용한 메서드**

```java
@Query("SECET p FROM Product AS p WHERE p.name = ?1") //?1 = 첫번째 파라미터를 지정
List<Product> findByName(String name); 
```

→ 단점: 파라미터의 순서가 바뀌면 오류가 발생한다.

### @Query 어노테이션과 @Param 어노테이션을 사용한 메서드

: 가독성이 높아지고 유지보수가 수월하다.

```java
@Query("SECET p FROM Product AS p WHERE p.name = :name")
List<Product> findByNameParam(@Param("name") String name); 

@Query("SECET p FROM Product AS p WHERE p.name LIKE %:name%")
List<Product> findByNameParam(@Param("name") String name); 
```

### **@Query 어노테이션으로 원하는 칼럼의 값만 추출**

```java
@Query("SECET p.name, p.price, p.stock FROM Product AS p WHERE p.name = :name")
List<Object[]> findByNameParam(@Param("name") String name); 
```