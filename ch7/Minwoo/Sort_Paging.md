# 정렬과 페이징

## 정렬

레코드 조회시 가격 혹은 등록 날짜를 기준으로 정렬이 필요한 경우가 있다.

쿼리 메서드를 활요하는 방법과 정렬을 위한 객체를 인자로 전달하는 방법이 있다.

### 쿼리 메서드를 활용하는 방법

```java
List<Product> findByNameOrderByPriceAsc(String name);
```

쿼리 메서드를 활용하는 방법은 메서드 이름이 매우 길어진다는 문제가 있다.

정렬을 위한 `Sort`객체를 인자로 전달하는 방법을 사용하면 메서드 이름을 짧게 작성할 수 있다.

### 파라미터로 Sort 정보를 받아오는 방법

```java
List<Product> products = productRepository.findByNameContains("네스프레소", Sort.by(Sort.Order.asc("name"), Sort.Order.asc("price")));
        /*
        Hibernate:
            select
                product0_.id as id1_0_,
                product0_.category as category2_0_,
                product0_.name as name3_0_,
                product0_.price as price4_0_
            from
                product product0_
            where
                product0_.name like ? escape ?
            order by
                product0_.name asc,
                product0_.price asc
        */
```
정렬 조건을 여러개 전달하는 것도 가능하다.

## 페이징

데이터를 n개씩 여러 페이지로 나눠 전달하는 것을 페이징이라고 한다.

페이징하는 방법은 간단하게 쿼리 메서드 인자에 Pageable 객체를 전달하면 된다.

```java
Page<Product> products = productRepository.findAll(PageRequest.of(1, 5));
        /*
        Hibernate:
            select
                product0_.id as id1_0_,
                product0_.category as category2_0_,
                product0_.name as name3_0_,
                product0_.price as price4_0_
            from
                product product0_ limit ? offset ?
        Hibernate:
            select
                count(product0_.id) as col_0_0_
            from
                product product0_
        */
System.out.println(products.getContent()); // 페이지 객체에 담긴 데이터를 가져오는 메서드.
```
+ `PageRequest.of(int page, int size)`
+ `PageRequest.of(int page, int size, Sort sort)`
+ ...

