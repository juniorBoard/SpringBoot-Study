# JPQL
JPQL ( JPA Query Language) → “JPA에서 사용할 수 있는 쿼리”

## SQL vs JPQL

- SQL : **테이블**이나 **칼럼 이름**을 사용
- JPQL : 매핑된 **엔티티의 이름**과 **필드의 이름**을 사용

  <JPQL 쿼리의 기본 구조>  
  <img src="https://user-images.githubusercontent.com/90807141/195055873-c72be455-409a-4445-9331-d5aff1df3c2e.png" width=60%>
  - **SELECT** : 가져오고자 하는 칼럼
  - **FROM** : 엔티티 타입 지정
  - **WHERE** : 조건 지정

## 쿼리 메서드
<리포지토리 쿼리 메서드 예시>   

<img src="https://user-images.githubusercontent.com/90807141/195056004-18e99c1c-0c53-4ec7-b255-541a1ae84715.png" width=70%>

## 주제 키워드
find…By  
read…By  
get…By  
query…By  
search…By  
stream…By
```java
Optional<Product> findByNumber(Long number);
List<Product> findAllByName(String name);
Product queryByNumber(Long number);
```
**exists…By**

: 특정 데이터가 존재하는지 확인하는 키워드 (Return Type : Boolean)

```java
boolean existsByNumber(Long number); //number로 데이터 존재 확인
```

**count…By**

: 조회 쿼리를 수행한 후 쿼리 결과로 나온 레코드의 개수를 리턴

```java
long countByName(String name); // name으로 조회된 레코드 갯수리턴
```

**delete…By, remove…By**

: 삭제쿼리를 수행. 리턴타입이 없거나 삭제한 횟수를 리턴

```java
void deleteByNumber(Long number); //number로 삭제
long removeByName(String name); //name으로 삭제
```

**…First<number>… , …Top<number>…**

: 쿼리를 통해 조회된 결괏값의 개수를 제한 → 한번의 동작으로 여러건을 조회할 때 사용

```java
List<Product> findFirst5ByName(String name); //처음 5개 조회
List<Product> findTop10ByName(String name); //상위 10개 조회
```

## 조건자 키워드

**Is**

: 값의 일치를 조건으로 사용 → 생략되는 경우가 많고 Equals와 동일한 기능을 한다.

```java
Product findByNumbersIs(Long number);
Product findByNumberEquals(Long number);
```

**(Is)Not**

: 값의 불일치를 조건으로 사용하는 조건자 키워드 ( Is 생략가능)

```java
Product findByNumberIsNot(Long number);
Product findByNumberNot(Long number);
```

**(Is)Null, (Is)NotNull**

: 값이 null 인지 검사하는 조건자 키워드

```java
List<Product> findByUpdatedAtNull();
List<Product> findByUpdatedAtIsNull();
List<Product> findByUpdatedAtNotNull();
List<Product> findByUpdatedAtIsNotNull();
```

**(Is)True, (Is)False**

: boolean 타입으로 지정된 칼럼값을 확인하는 키워드(실제코드에서는 에러가 발생한다.)

```java
Product findByisActiveTrue();
Product findByisActiveIsTrue();
Product findByisActiveFalse();
Product findByisActiveIsFalse();
```

**And,Or**

: 여러 조건을 묶을때 사용

```java
Product findByNumberAndName(Long number, String name);
Product findByNumberOrName(Long number, String name);
```

**(Is)GreaterThan, (Is)LessThan, (Is)Between**

: 숫자나 datetime 칼럼을 대상으로 한 비교 연산에 사용할 수 있는 키워드

→ (Is)GreaterThan : 초과 ,  (Is)LessThan : 미만

```java
List<Product> findByPriceIsGreaterThan(Long price); //초과
List<Product> findByPriceGreaterThan(Long price); //초과
List<Product> findByPriceGreaterThanEqual(Long price); //이상

List<Product> findByPriceIsLessThan(Long price); //미만
List<Product> findByPriceLessThan(Long price); //미만
List<Product> findByPriceLessThanEqual(Long price); //이하

List<Product> findByPriceIsBetween(Long lowPrice, Long highPrice); //사이값
List<Product> findByPriceBetween(Long lowPrice, Long highPrice); //사이값
```

**(Is)StartingWith(=StartsWith), (Is)EndingWith(=EndsWith),**

**(Is)Containing(=Contains), (Is)Like**

: 칼럼값에서 일부 일치 여부를 확인하는 조건자 키워드 → 문자열 추출하는 ‘%’ 키워드와 동일

```java
//코드수준에서 메서드 호출시 전달값에 '%'를 명시적으로 입력
List<Product> findByNameLike(String name);
List<Product> findByNameIsLike(String name);

//문자열의 양 끝에 '%'배치
List<Product> findByNameContains(String name);
List<Product> findByNameContaining(String name);
List<Product> findByNameIsContaining(String name);

//문자열의 앞에 '%'배치
List<Product> findByNameStartsWith(String name);
List<Product> findByNameStartingWith(String name);
List<Product> findByNameIsStartingWith(String name);

//문자열의 끝에 '%'배치
List<Product> findByNameEndsWith(String name);
List<Product> findByNameEndingWith(String name);
List<Product> findByNameIsEndingWith(String name);
```
