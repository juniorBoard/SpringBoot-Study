package com.example.paging.repository;

import com.example.paging.entity.Product;
import com.example.paging.entity.QProduct;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class ProductRepositoryTest {

    @Autowired
    ProductRepository productRepository;
    JPAQueryFactory jpaQueryFactory;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    public void pagingTest() {
        // given
        Product product1 = new Product();
        product1.setName("펜");
        product1.setPrice(1000);
        product1.setStock(100);

        Product product2 = new Product();
        product2.setName("펜");
        product2.setPrice(5000);
        product2.setStock(300);

        Product product3 = new Product();
        product3.setName("펜");
        product3.setPrice(500);
        product3.setStock(50);

        Product product4 = new Product();
        product4.setName("펜");
        product4.setPrice(600);
        product4.setStock(40);

        Product product5 = new Product();
        product5.setName("펜");
        product5.setPrice(500);
        product5.setStock(30);

        Product savedProduct1 = productRepository.save(product1);
        Product savedProduct2 = productRepository.save(product2);
        Product savedProduct3 = productRepository.save(product3);
        Product savedProduct4 = productRepository.save(product4);
        Product savedProduct5 = productRepository.save(product5);

        System.out.println(productRepository.findByNameOrderByNumberAsc("펜"));
        System.out.println(productRepository.findByNameOrderByNumberDesc("펜"));

        System.out.println(productRepository.findByNameOrderByPriceAscStockDesc("펜"));

        System.out.println(productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"))));
        System.out.println(productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"), Sort.Order.desc("stock"))));

        List<Product> search = productRepository.findByName("펜", Sort.by(Sort.Order.asc("price"), Sort.Order.desc("stock")));
        for (Product product : search) {
            System.out.println(product);
        }

        System.out.println(productRepository.findByName("펜", PageRequest.of(0, 2)));

        Page<Product> productPage = productRepository.findByName("펜", PageRequest.of(0, 2));
        System.out.println(productPage.getContent());

        System.out.println(productRepository.findByName("펜", PageRequest.of(0, 2, Sort.by(Sort.Order.asc("price")))));
        System.out.println(productRepository.findByName("펜", PageRequest.of(0, 2, Sort.by(Sort.Order.asc("price")))).getContent());
    }

    @Test
    public void queryAnnotationTest() {
        System.out.println(productRepository.findByName("펜"));
        System.out.println(productRepository.findByNameParam("펜"));

        List<Object[]> objects = productRepository.findByNameParam2("펜");
        for (Object[] object : objects) {
            System.out.println(object[0]);
            System.out.println(object[1]);
            System.out.println(object[2]);
        }
    }

    //JPAQuery
    @Test
    public void queryDslTest() {
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

    //JPAQueryFactory - selectFrom()
    @Test
    public void queryDslTest2() {
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> productList = jpaQueryFactory
                .selectFrom(qProduct)
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

    //JPAQueryFactory - select(), form()
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

    //JPAQueryFactory Bean 등록
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

    //QuerydslPredicateExecutor - predicate
    @Test
    public void queryDSLTest5() {
        Predicate predicate = QProduct.product.name.containsIgnoreCase("펜")
                .and(QProduct.product.price.between(1000, 2500));

        Optional<Product> foundProduct = productRepository.findOne(predicate);

        if(foundProduct.isPresent()){
            Product product = foundProduct.get();
            System.out.println(product.getNumber());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getStock());
        }
    }

    //QuerydslPredicateExecutor - 서술부로 조회
    @Test
    public void queryDSLTest6() {
        QProduct qProduct = QProduct.product;

        Iterable<Product> productList = productRepository.findAll(
                qProduct.name.contains("펜")
                        .and(qProduct.price.between(550, 1500))
        );

        for (Product product : productList) {
            System.out.println(product.getNumber());
            System.out.println(product.getName());
            System.out.println(product.getPrice());
            System.out.println(product.getStock());
        }
    }

}