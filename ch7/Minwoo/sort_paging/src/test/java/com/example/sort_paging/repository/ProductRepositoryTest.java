package com.example.sort_paging.repository;

import com.example.sort_paging.domain.Product;
import com.example.sort_paging.domain.QProduct;
import com.querydsl.jpa.impl.JPAQuery;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void findByNameContains() {
        // 이름에 "네스프레소"가 포함된 상품 조회
        List<Product> searchByKeyword = productRepository.findByNameContains("네스프레소");
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
        */
        for (Product product : searchByKeyword) {
            System.out.println(product);
        }
    }

    @Test
    void findByPriceGreaterThanEqual() {
        // 100,000원 이상의 상품 조회
        List<Product> searchByKeyword = productRepository.findByPriceGreaterThanEqual(100000);
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
                product0_.price>=?
        */
        for (Product product : searchByKeyword) {
            System.out.println(product);
        }
    }

    @Test
    void findByCategoryContainsAndPriceGreaterThan() {
        // "커피머신" 카테고리 중 100,000원 이상의 상품 조회
        List<Product> searchByKeyword = productRepository.findByCategoryContainsAndPriceGreaterThan("커피머신", 100000);
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
                (
                    product0_.category like ? escape ?
                )
                and product0_.price>?
        */
        for (Product product : searchByKeyword) {
            System.out.println(product);
        }
    }

    @Test
    void findAll() {
        List<Product> products = productRepository.findAll();
        /*
        Hibernate:
            select
                product0_.id as id1_0_,
                product0_.category as category2_0_,
                product0_.name as name3_0_,
                product0_.price as price4_0_
            from
                product product0_
        */
        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    void findAllByOrderByPriceAsc() {
        List<Product> products = productRepository.findAllByOrderByPriceAsc();
        /*
        Hibernate:
            select
                product0_.id as id1_0_,
                product0_.category as category2_0_,
                product0_.name as name3_0_,
                product0_.price as price4_0_
            from
                product product0_
            order by
                product0_.price asc
        */
        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    void findByNameContainsOrderByPriceAsc() {
        List<Product> products = productRepository.findByNameContainsOrderByPriceAsc("네스프레소");
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
                product0_.price asc
        */
        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    void findByNameContains2() {
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
        for (Product product : products) {
            System.out.println(product);
        }
    }
    @Test
    void findAllPaging() {
        Page<Product> products = productRepository.findAll(PageRequest.of(0, 5));
        /*
        Hibernate:
            select
                product0_.id as id1_0_,
                product0_.category as category2_0_,
                product0_.name as name3_0_,
                product0_.price as price4_0_
            from
                product product0_ limit ?
        Hibernate:
            select
                count(product0_.id) as col_0_0_
            from
                product product0_
        */
        System.out.println(products.getContent());
    }
    @Test
    void findAllPaging2() {
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
        System.out.println(products.getTotalPages());
        System.out.println(products.getContent());
    }
    @Test
    void findByCategory() {
        List<Product> products = productRepository.findByCategory("커피머신");
        for (Product product : products) {
            System.out.println(product);
        }
    }
    @Test
    void findByCategory2() {
        List<Product> products = productRepository.findByCategory2("커피");
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
                product0_.category like ?
        */
        for (Product product : products) {
            System.out.println(product);
        }
    }
    @Test
    void findByCategory3() {
        List<Object[]> products = productRepository.findByCategory3("커피");
        /*
        Hibernate:
            select
                product0_.name as col_0_0_,
                product0_.category as col_1_0_,
                product0_.price as col_2_0_
            from
                product product0_
            where
                product0_.category like ?
        */
        for (Object product : products) {
            System.out.println(product);
        }
    }
    @PersistenceContext
    EntityManager entityManager;

    @Test
    void queryDslTest() {
        JPAQuery<Product> query = new JPAQuery<>(entityManager);
        QProduct qProduct = QProduct.product;

        List<Product> products = query.from(qProduct)
                                        .where(qProduct.name.contains("네스프레소"))
                                        .orderBy(qProduct.price.asc())
                                        .fetch();

        for (Product product : products) {
            System.out.println(product);
        }
    }
}