## 정렬처리

### 0. **ORDER BY구문을 사용**

```java
// 이름으로 검색후 상품번호로 정렬
List<Product> findByNameOrderByNumberAsc(String name); //오름차순
List<Product> findByNameOrderByNumberDesc(String name); //내림차순

```

**여러개 처리**

: And,Or을 사용하지 않고 우선순위로 작성

```java
//가격 기준으로 오름차순 정렬 후, 재고수량을 기준으로 내림차순 정렬
List<Product> findByNameOrderByPriceAscStockDesc(String name);
```

단점. 조건이 많아질 수록 메서드의 이름이 길어진다.

### 1. **매개변수를 활용한 쿼리 정렬**

```java
List<Product> findByName(String name, Sort sort);
```

: Sort 객체를 활용해 매개변수로 받아들인 정렬기준으로 작성

[test code]

```java
productRepository.findByName("펜", Sort.by(Order.asc("price")));
productRepository.findByName("펜", Sort.by(Order.asc("price"), Order.desc("Stock")));
```

### 2. 메서드로 분리

: Sort 부분을 하나의 메소드로 분리

```java
@Test
void sortingAndPagingTest() {
		(생략)

		System.Out.println(productRepository.findByName("펜", getSort()));
}

private Sort getSort() {
		return Sort.by(
						Order.asc("price"),
						Order.desc("stock")
		);
}
```

## 페이징 처리

: 데이터베이스의 레코드를 개수로 나눠 페이지를 구분하는 것

→Page, Pageable을 사용

```java
Page<Product> findByName(String name, Pageableu pageable);
```

→ return 타입으로 Page를 설정, 매개변수로 Pageable 타입의 객체를 정의

```java
//사용 예시
Page<Product> productPage = productRepository.findByName("펜", PageRequest.of(0, 2));

//출력
Page 1 of 2 containing com.springboot...
Page 객체를 그대로 출력하며 객체의 값은 보여지지 않고 해당 페이지가 출력된다.

//객체의 데이터 출력하는 방법 (배열 형태로 출력된다.)
Page<Product> productPage = productRepository.findByName("펜", PageRequest.of(0, 2));
System.out.println(productPage.getContent());
```

→ PageRequest = Pageable의 구현체

**of 메서드**

of(int page, int size) : 페이지 번호(0부터 시작), 페이지당 데이터 개수 → 데이터 정렬x

of(int page, int size, Sort) : 페이지번호, 페이지당 데이터 개수, 정렬 → sort에 의해 정렬

of(int page, int size, Direction, String… properties) : …, 정렬방향, 속성(칼럼)

→ Sort.by(direction, properties)에 의해 정렬