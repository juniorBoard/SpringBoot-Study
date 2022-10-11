### @QueryDSL 사용법
0. **gradle 세팅**  
   1. build.gradle 설정
      - plugin 추가 : id "com.ewerk.gradle.plugins.querydsl" version "1.0.10"
      - dependencies 추가 :
        - implementation 'com.querydsl:querydsl-apt:5.0.0'
        - implementation 'com.querydsl:querydsl-jpa:5.0.0'
      - 기타 설정
        ```java
        // querydsl에서 사용할 경로 설정
        def querydslDir = "$buildDir/generated/querydsl"
        // JPA 사용 여부와 사용할 경로를 설정
        querydsl {
        jpa = true
        querydslSourcesDir = querydslDir
        }
        // build 시 사용할 sourceSet 추가
        sourceSets {
        main.java.srcDir querydslDir
        }
        // querydsl 컴파일시 사용할 옵션 설정
        compileQuerydsl{
        options.annotationProcessorPath = configurations.querydsl
        }
        // querydsl 이 compileClassPath 를 상속하도록 설정
        configurations {
        compileOnly {
        extendsFrom annotationProcessor
        }
        querydsl.extendsFrom compileClasspath
        }
        ```
   2. Gradle/ProjectName/Tasks/other/compileQuerydsl 실행
   3. build/generated/querydls 하위에 Q도메인 클래스 파일이 생성되게 된다.
    

1. **JPAQuery를 활용하여 @QueryDSL에 의해 생성된 Q도메인 클래스를 활용하는 코드**

    ```java
    @PersistenceContext
    EntityManager entityManager;
    
    @Test
    void queryDslTest() {
        JPAQuery<Product> query = new JPAQuery(entityManager);
        QProduct qProduct = QProduct.product;
    
        List<Product> productList = query
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();
    
        for (Product product : productList) {
            System.out.println("----------------");
            System.out.println();
            System.out.println("Product Number : " + product.getNumber());
            System.out.println("Product Name : " + product.getName());
            System.out.println("Product Price : " + product.getPrice());
            System.out.println("Product Stock : " + product.getStock());
            System.out.println();
            System.out.println("----------------");
        }
    }
    ```

   사용 가능한 반환 메서드

    - List<T> fetch() : 조회 결과를 리스트로 반환
    - T fetchOne : 단 한건의 조회 결과를 반환
    - T fetchFirst() : 여러 건의 조회 결과 중 1건을 반환
    - Long fetchCount() : 조회 결과의 개수를 반환
    - QueryResult<T> fetchResults() : 조회 결과 리스트와 개수를 포함한 QueryResults를 반환
2. **JPAQueryFactory 를 활용한 방법**

    ```java
    @PersistenceContext
    EntityManager entityManager;
    
    @Test
    void queryDslTest2() {
    
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;
    
        List<Product> productList = jpaQueryFactory.selectFrom(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();
    
        for (Product product : productList) {
            System.out.println("----------------");
            System.out.println();
            System.out.println("Product Number : " + product.getNumber());
            System.out.println("Product Name : " + product.getName());
            System.out.println("Product Price : " + product.getPrice());
            System.out.println("Product Stock : " + product.getStock());
            System.out.println();
            System.out.println("----------------");
        }
    }
    ```

    - JPAQueryFactory에서는 select 절부터 작성 가능하다.
3. **전체 칼럼이 아닌 일부만 조회 하는 방법**

   selectFrom() → select(), from() 메서드로 구분해서 사용

    ```java
    @PersistenceContext
    EntityManager entityManager;
    
    @Test
    void queryDslTest3() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;
    
        List<String> productList = jpaQueryFactory
                .select(qProduct.name)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();
    
        for (String product : productList) {
            System.out.println("----------------");
            System.out.println("Product Name : " + product);
            System.out.println("----------------");
        }
    		
            // 조회 대상이 여러 개일 경우
        List<Tuple> tupleList = jpaQueryFactory
                .select(qProduct.name, qProduct.price)
                .from(qProduct)
                .where(qProduct.name.eq("펜"))
                .orderBy(qProduct.price.asc())
                .fetch();
    
        for (Tuple product : tupleList) {
            System.out.println("----------------");
            System.out.println("Product Name : " + product.get(qProduct.name));
            System.out.println("Product Name : " + product.get(qProduct.price));
            System.out.println("----------------");
        }
    }
    ```

4. **실제 비즈니스 로직에서 활용하는 방법**
    1. QueryDSL 컨피그 파일 생성

       **JPAQueryFactory 객체를 @Bean 객체로 등록**

        ```java
        @Configuration
        public class QueryDSLConfiguration {
        
            @PersistenceContext
            EntityManager entityManager;
        
            @Bean
            public JPAQueryFactory jpaQueryFactory(){
                return new JPAQueryFactory(entityManager);
            }
        }
        ```

    2. JPAQueryFactory 빈을 활용

        ```java
        @Autowired
        JPAQueryFactory jpaQueryFactory;
        
        @Test
        void queryDslTest4() {
            QProduct qProduct = QProduct.product;
        
            List<String> productList = jpaQueryFactory
                    .select(qProduct.name)
                    .from(qProduct)
                    .where(qProduct.name.eq("펜"))
                    .orderBy(qProduct.price.asc())
                    .fetch();
        
            for (String product : productList) {
                System.out.println("----------------");
                System.out.println("Product Name : " + product);
                System.out.println("----------------");
            }
        }
        ```