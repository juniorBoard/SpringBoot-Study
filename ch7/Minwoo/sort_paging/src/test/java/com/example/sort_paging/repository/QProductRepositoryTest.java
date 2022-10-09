package com.example.sort_paging.repository;

import com.example.sort_paging.domain.Product;
import com.example.sort_paging.domain.QProduct;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class QProductRepositoryTest {
    @Autowired
    JPAQueryFactory jpaQueryFactory;
    @Autowired
    QProductRepository qProductRepository;

    @Test
    void queryDslTest01() {
        QProduct qProduct = QProduct.product;

        List<Product> products = jpaQueryFactory
                .select(qProduct)
                .from(qProduct)
                .where(qProduct.category.eq("커피머신"))
                .orderBy(qProduct.price.asc())
                .fetch();

        for (Product product : products) {
            System.out.println(product);
        }
    }

    @Test
    void queryDslTest02() {
        Predicate predicate = QProduct.product.category.contains("커피")
                .and(QProduct.product.price.goe(150_000));

        Iterable<Product> products = qProductRepository.findAll(predicate);

        for (Product product : products) {
            System.out.println(product);
        }
    }
}