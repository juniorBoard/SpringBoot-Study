# JPQL(Java Persistence Query Language)
JPA만의 기술을 사용했을 때는 DB의 데이터 조회를 식별자를 통한 조회와 객체 그래프를 통한 탐색만 가능했다.

- 식별자를 통한 조회: `em.find()`
- 객체 그래프 탐색: `a.getB().getC()`

두 가지 조회 방법으로만 어플리케이션 개발을 한다면 모든 엔티티를 메모리에 올려두고 애플리케이션에서 필터링하는 등의 성능 낭비가 발생하게 된다.

이러한 낭비를 막기 위해 JPA는 데이터베이스에서 상황별 조건에 맞게 필터링하여 필요한 데이터를 가져오는 JPQL을 제공한다.

JPQL은 Jaca Persistence Query Language의 약자로 JPA에서 SQL을 추상화하여 만든 객체 지향 쿼리 언어이다.

SQL을 추상화하였기에 특정 데이터베이스에 의존적이지 않다.

`SELECT`, `FROM`, `WHERE`, `GROUP BY`, `HAVING`, `JOIN` 등 SQL과 문법이 유사하다.

SQL은 데이터베이스 테이블을 대상으로 쿼리를 작성하지만 JPQL은 엔티티 객체를 대상으로 쿼리를 작성한다.

`select m From Member m where m.name like ‘%hello%'` 에서 Member는 테이블 이름이 아니다.

JPQL에서는 테이블이 아닌 @Entity(name = “”)에 지정된 엔티티 이름을 가르키고 있다.                           
(지정하지 않았다면 default 값 → 클래스 이름)

<br><br>

---

## 기본 문법

- JPQL은 엔티티와 속성은 대소문자를 구분한다.                 
  하지만 SELECT, FROM, WHERE과 같은 JPQL 키워드는 대소문자를 구분하지 않는다.

- from Member m에서 m과 같은 별칭(alias)은 필수이다.                  
  하지만 SQL문에서 별칭 지정을 위해 사용하는 as는 생략 가능하다.                
  ex) `SELECT m FROM Member m` , `SELECT m.team FROM Member m`

<br>

### createQuery의 반환 타입
JPQL 실행 시 쿼리 객체 생성이 필요하다.

순수 JPA의 JPQL은 쿼리를 실행할 때 EntityManager의 `createQuery()` , `createNamedQuery()`, `createNativeQuery()` 등의 메서드를 사용해야 한다.
```java
public interface EntityManager {
    public Query createQuery(String qlString);
    public <T> TypedQuery<T> createQuery(CriteriaQuery<T> criteriaQuery);
    public Query createQuery(CriteriaUpdate updateQuery);
    public Query createQuery(CriteriaDelete deleteQuery);
    public <T> TypedQuery<T> createQuery(String qlString, Class<T> resultClass);
    ...
}
```
각각의 `createQuery()` 메서드의 반환값을 살펴보면 `Query`, `TypedQuery` 두 종류가 존재한다.

`TypeQuery`: 반환 타입이 명확하게 지정할 수 있을 경우 사용                              
아래의 두 명령어와 같이 쿼리의 반환 타입이 동일한 경우 TypedQuery를 사용할 수 있다.
```java
TypedQuery<Member> result1 = em.createQuery("select m from Member as m", Member.class);
TypedQuery<String> result2 = em.createQuery("select m.username from Member as m", String.class);
```
<br>

`Query`: 반환 타입이 명확하게 지정할 수 없을 경우 사용                                
아래의 코드와 같이 쿼리의 반환 타입이 여러개인 경우 Query타입으로 return 받아야 한다.
```java
Query result3 = em.createQuery("select m.username, m.age from Member as m");
```

<br><br>

---

## 쿼리 메소드
* [공식문서](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)


### 쿼리 메소드의 생성
쿼리 메소드는 Repository 인터페이스에 간단한 네이밍 룰을 이용하여 메소드를 작성하면 원하는 쿼리를 실행할 수 있다.


ex)
- `sampleRepository.findByItemNm("AAA");` //상품명이 AAA인 것을 조회
- `List<Dog> puppyList = sampleRepository.findByDogNameOrDogAge("COCO","6");` // 강아지 이름이 COCO 또는 강아지 나이가 6 인 것을 조회
- `sampleRepository.findByPriceLessThanOrderByPriceDesc(50000);` // 50000원 아래의 상품들을 내림차순 정렬

<br><br>

### 쿼리 메소드의 주제 키워드
**공식 문서 : Query subject keywords**

| Keyword | Description |
| --- | --- |   
| find…By, read…By, get…By, query…By, search…By, stream…By | General query method returning typically the repository type, a Collection or Streamable subtype or a result wrapper such as Page, GeoResults or any other store-specific result wrapper. Can be used as findBy…, findMyDomainTypeBy… or in combination with additional keywords. |
| exists…By | Exists projection, returning typically a boolean result. |
| count…By | Count projection returning a numeric result. |
| delete…By, remove…By | Delete query method returning either no result (void) or the delete count. |
| …First<number>…, …Top<number>… | Limit the query results to the first <number> of results. This keyword can occur in any place of the subject between find (and the other keywords) and by. |
| …Distinct… | Use a distinct query to return only unique results. Consult the store-specific documentation whether that feature is supported. This keyword can occur in any place of the subject between find (and the other keywords) and by. |

<br><br>

**find..by, read..by, get..by, query..by 등**
- 조회 기능을 수행하는 키워드
- '..'의 영역은 엔티티를 표현할 수 있으나 Repository에서 이미 엔티티를 정의하고 있기 때문에 생략하는 경우가 많음
- 리턴 타입은 Collection이나 Streamable에 속하는 타입을 설정할 수 있음

**exists..by**
- 특정 데이터가 존재하는지 확인하는 기능을 수행하는 키워드
- 리턴 타입은 boolean 속성을 사용

**count..by**
- 조회 쿼리를 수행한 후 결과 개수를 리턴하는 키워드
- long 타입으로 리턴

**delete..by, remove..by**
- 삭제 쿼리를 수행
- 리턴 타입이 없거나 삭제한 횟수를 리턴

**...First(number)..., ...Top(number)...**
- 쿼리를 통해 조회되는 결과값의 수를 제한하는 키워드
- 일반적으로 여러 건을 조회하기 위해 사용되지만 단 건으로 조회할 경우 (number) 부분을 생략하면 됨

<br><br>
  
---

### 쿼리 메소드의 조건자 키워드
**공식 문서 - Query predicate keywords**

| Logical keyword | Keyword expressions |
| --- | --- |
| AND | And |
| OR | Or |
| AFTER | After, IsAfter |
| BEFORE | Before, IsBefore |
| CONTAINING | Containing, IsContaining, Contains |
| BETWEEN | Between, IsBetween |
| ENDING_WITH | EndingWith, IsEndingWith, EndsWith |
| EXISTS | Exists |
| FALSE | False, IsFalse |
| GREATER_THAN | GreaterThan, IsGreaterThan |
| GREATER_THAN_EQUALS | GreaterThanEqual, IsGreaterThanEqual |
| IN | In, IsIn |
| IS | Is, Equals, (or no keyword) |
| IS_EMPTY | IsEmpty, Empty |
| IS_NOT_EMPTY | IsNotEmpty, NotEmpty |
| IS_NOT_NULL | NotNull, IsNotNull |
| IS_NULL | Null, IsNull |
| LESS_THAN | LessThan, IsLessThan |
| LESS_THAN_EQUAL | LessThanEqual, IsLessThanEqual |
| LIKE | Like, IsLike |
| NEAR | Near, IsNear |
| NOT | Not, IsNot |
| NOT_IN | NotIn, IsNotIn |
| NOT_LIKE | NotLike, IsNotLike |
| REGEX | Regex, MatchesRegex, Matches |
| STARTING_WITH | StartingWith, IsStartingWith, StartsWith |
| TRUE | True, IsTrue |
| WITHIN | Within, IsWithin |

<br><br>

**Is**
- 값의 일치를 위한 조건자 키워드
- Equals 키워드와 동일한 기능을 수행

**(Is)Not**
- 값의 불일치를 위한 조건자 키워드
- Is는 생략하고 Not 키워드만 사용할 수 있음

**(Is)Null, (Is)NotNull**
- 해당 컬럼의 레코드의 값이 Null인지 아닌지 체크하는 키워드

**(Is)True , (Is)False**
- boolean 타입으로 지정되어 있는 컬럼의 값을 확인하는 키워드

**And, Or**
- 여러 조건을 묶을 때 사용

**(Is)GreaterThan, (Is)LessThan, (Is)Between**
- 숫자나 DateTime 컬럼에서 사용할 수 있는 비교 연산 키워드
- 경계값을 포함하기 위해서는 Equl 키워드를 추가해야 함

**(Is)StartingWith(==StartsWith), (Is)EndingWith(==EndsWith), (Is)Containing(==Contains),(Is)Like**
- 컬럼의 값에서 값이 일부 일치하는지 확인하는 키워드
- SQL 문으로 가공될 때 Containing 키워드는 양 끝, StartingWith는 앞, EndingWith는 뒤에 %가 포함됨
- Like 키워드는 %를 명시적으로 기입해줘야 함

<br><br>

---

### 정렬과 페이징 처리
정렬 처리하기

#### 페이징 처리
페이징을 위한 데이터 조회를 하기 위해서는 아래와 같이 몇 페이지부터 몇 개의 데이터를 가져올지의 설정이 필요해 JPQL parameter binding으로 구현시 많은 어려움을 느낄 수 있다.
```sql
select *
from member m
order by m.name desc limit ?, ?;
```
<br>

JPA에서는 페이징 쉽게 할 수 있도록 `setFirstResult(in startPosition)`과 `setMaxResults(int maxResult)` 메서드를 제공하여 개발자가 쉽게 페이징을 구현할 수 있다.

- `setFirstResult(in startPosition)`: 조회 시작 위치
- `setMaxResults(int maxResult)` : 조회할 데이터 수

```java
//페이징 쿼리
String jpql = "select m from Member m order by m.name desc"; 
List<Member> resultList = em.createQuery(jpql, Member.class)
                            .setFirstResult(10)
                            .setMaxResults(20)
                            .getResultList();
```

<br>

```java
TypeQuery<Member> query =     em.createQuery("SELECT m FROM Member m ORDER BY m.username DESC", Member.class); 
  
// 11~30번 데이터 조회
query.setFirstResult(10);    // 조회 시작 위치
query.setMaxResults(20);    // 조회할 데이터 수
query.getResultList()
```
<br><br>
  
---

### @Query 어노테이션 사용하기
JPQLQuery 인터페이스는 JPQL 쿼리를 위한 Query 인터페이스이다.

JPAQuery 클래스는 JPQLQuery 인터페이스를 구현한 클래스이며, 쿼리를 작성하고 실행하는 역할을 한다.

JPAQueryFactory도 JPAQuery를 생성해주는 factory클래스이다.

Querydsl은 자바 코드 기반으로 쿼리를 작성하게 해준다.

<br><br>

### QueryDsl 적용하기

#### QueryDSL 이란?
Querydsl 정적 타입을 이용해서 SQL과 같은 쿼리를 생성할 수 있도록 해 주는 프레임워크

<br><br>

#### QueryDSL 장점
- 문자가 아닌 코드로 쿼리를 작성함으로써, 컴파일 시점에 문법 오류를 쉽게 확인할 수 있다.
- 자동 완성 등 IDE의 도움을 받을 수 있다.
- 동적인 쿼리 작성이 편리하다.
- 쿼리 작성 시 제약 조건 등을 메서드 추출을 통해 재사용할 수 있다.

<br><br>

#### QueryDSL 을 사용하기 위한 프로젝트 설정
**gradle 설정**
```java
dependencies {
    // ... 생략
    
    implementation "com.querydsl:querydsl-core:${queryDslVersion}"
    implementation "com.querydsl:querydsl-jpa:${queryDslVersion}"

    /*
     * `NoClassDefFoundError` 관련 대응으로 필요하다.
     * 참고로 javax -> jakarta 로 이름이 변경되었다.
     */
    annotationProcessor(
            "jakarta.persistence:jakarta.persistence-api",
            "jakarta.annotation:jakarta.annotation-api",
            "com.querydsl:querydsl-apt:${queryDslVersion}:jpa")
}

sourceSets {
    main {
        java {
            srcDirs = ["$projectDir/src/main/java", "$projectDir/build/generated"]
        }
    }
}
```  

<br><br>

**Config 설정**
```java
@Configuration
public class QueryDSLConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(entityManager);
    }
}
```  
JPAQueryFactory를 주입받아 QueryDSL을 사용할 수 있다.

jpaQueryFactory 빈을 Repository에서 사용하게 된다.

<br><br>

#### 기본적인 QueryDSL 사용하기
**Entity 클래스 정의**
먼저 엔티티(Entity) 클래스를 정의한다. 각각 article과 user 테이블에 매핑되는 엔티티다.
```java
@Getter
@Entity
@Table(name = "article")
public class Article {
	@Id
	private Integer id;
	@Column(name = "user_id")
	private Integer userId;
	private String title;
}
```

```java
@Getter
@Entity
@Table(name = "user")
public class User {
	@Id
	private Integer id;
	private String name;
	private String level;
}
```  
<br><br>

**Q클래스 생성**  
Querydsl은 컴파일 단계에서 엔티티를 기반으로 Q클래스 파일들을 생성한다.       
이 클래스를 기반으로 쿼리를 작성하게 된다.

Q클래스를 생성하려면 Gradle 옵션을 통해서 소스 코드를 컴파일시키면 된다.               
즉, build task의 build 옵션을 실행하거나 단순히 Q클래스만 만들 목적이라면 other 태스크의 compileJava만 실행시키면 된다.

![image](https://user-images.githubusercontent.com/74857364/194740410-d297f52f-ba39-45e7-b9d4-c91ebfbd3773.png)

<br><br>

**Repository 정의**
제 쿼리를 작성하고 수행할 Repository 레이어들을 만든다.              
JPA 인터페이스 메서드와 Querydsl 기반으로 사용할 메서드를 모두 사용할 것이다.

먼저 구현할 Querydsl 메서드의 시그니처를 정의한다. `~RepositoryCustom` 이라는 네이밍을 갖는다.
```java
/**
 * Querydsl로 작성할 쿼리는 이 곳에 시그니처를 선언하고 `~RepositoryImpl`에서 구현한다.
 */
public interface ArticleRepositoryCustom {
	List<Article> findByLevelUsingQuerydsl(String level);
}
```

<br><br>

위에서 정의한 시그니처 기반으로 실제 동작을 정의할 구현체다.

QuerydslConfig 클래스에서 등록한 JPAQueryFactory를 기반으로 쿼리를 작성하고 수행한다.

메서드 네이밍은 임의로 “~UsingQuerydsl”라는 접미사를 붙였다.

```java
/**
 * Querydsl를 이용한 쿼리를 작성한다.
 */
@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {

	private final JPAQueryFactory queryFactory;

	public List<Article> findByLevelUsingQuerydsl(String level) {
		// Q클래스를 이용한다.
		QArticle article = QArticle.article;
		QUser user = QUser.user;

		return queryFactory.selectFrom(article)
			.where(
				article.userId.in(
					JPAExpressions
						.select(user.id)
						.from(user)
						.where(user.level.gt(level))
				)
			)
			.fetch();
	}
}
```

위와 같이 커스텀한 Repository은 네이밍 규약을 잘 지켜야 한다.

별도의 설정을 하지 않았다면, ~Impl 접미사를 붙여야만 스프링이 찾을 수 있다.

관련해서는 spring-data에 포함된 RepositoryConfigurationSourceSupport 클래스와 AnnotationRepositoryConfigurationSource 클래스의 내부 코드를 보면 알 수 있다.

마지막으로 JPA 인터페이스 메서드도 같이 사용할 수 있도록 인터페이스를 정의한다.

아래 findByLevel은 Querydsl과 비교하기 위해 추가했다.

<br><br>

```java
public interface ArticleRepository extends JpaRepository<Article, Integer>, ArticleRepositoryCustom {
	@Query(value = "SELECT * FROM article WHERE user_id IN (SELECT id FROM user WHERE level > :level)", nativeQuery = true)
	List<Article> findByLevel(String level);
}
```

<br><br>

출처
[[JPA] JPQL이란?](https://seongwon.dev/Spring/20220829-JPA-JPQL/)                   
[Spring Data JPA(쿼리 메소드)](https://kihwan95.tistory.com/5)                   
[Spring Data JPA - 쿼리 메서드 기능](https://ssdragon.tistory.com/96)                                          
[Spring Data JPA - Reference Documentation](https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods)                   
[쿼리 메소드](https://velog.io/@tjdrn990423/%EC%BF%BC%EB%A6%AC-%EB%A9%94%EC%86%8C%EB%93%9C)                  
[[JPA] JPQL Query 정리](https://data-make.tistory.com/614)                   
[QueryDSL](https://velog.io/@tigger/QueryDSL)           
[Querydsl: 소개와 사용법](https://madplay.github.io/post/introduction-to-querydsl)     
  
