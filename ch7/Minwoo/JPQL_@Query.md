# JPQL과 _@Query_

## JPQL

JPQL : JPA Query Language

SQL문법과 거의 비슷하다.

SQL과 차이점은 테이블이나 컬럼의 이름을 사용하는 것이 아니라 엔티티 객체의 이름과 필드 이름을 사용한다.

```jpaql
SELECT p FROM Product AS p WHERE p.name = ?1;
```

### SELECT

조회할 컬럼을 선택.

위 예시처럼 엔티티를 입력하면 전체 컬럼을 조회.
> SQL : SELECT * ...

### FROM

조회할 엔티티 선택.

### AS

조회 조건을 정의.

`AS`를 생략하고 `SELECT p FROM Product p`라고 작성 가능.

### WHERE
조회 조건을 정의.
`컬럼 = 값`형식으로 작성.

## @Query

### 예시 코드

```java
@Query("SELECT p FROM Product AS p WHERE p.category = ?1")
List<Product> findByCategory(String category);

@Query("SELECT p FROM Product p WHERE p.category LIKE %:category%")
List<Product> findByCategory2(@Param("category") String category);

@Query("SELECT p.name, p.category, p.price FROM Product p WHERE p.category LIKE %:category%")
List<Object[]> findByCategory3(@Param("category") String category);
```
#### _@Query_ 어노테이션

JPQL을 직접 작성하여 사용한다.

직접 사용하는 데이터베이스의 SQL문을 작성해도 가능하다. 단, 데이터베이스를 변경하게 되면 SQL문도 바꿔야한다.

<br>

#### _@Param_ 어노테이션

메서드 인자를 쿼리문에 사용하는 방법은 두 가지이다.

메서드 인자의 위치를 쿼리문에 넣어주는 방법. _@Param_ 어노테이션을 사용하는 방법.

전자의 경우 파라미터의 순서가 바뀌면 오류가 발생할 가능성이 있다.

따라서, _@Param_ 어노테이션을 사용하는 것을 권장한다.

