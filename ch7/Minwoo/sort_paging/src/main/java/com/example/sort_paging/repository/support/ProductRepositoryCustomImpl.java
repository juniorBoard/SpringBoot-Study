package com.example.sort_paging.repository.support;

import com.example.sort_paging.domain.Product;
import com.example.sort_paging.domain.QProduct;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

public class ProductRepositoryCustomImpl extends QuerydslRepositorySupport implements ProductRepositoryCustom {

    public ProductRepositoryCustomImpl() {
        super(Product.class);
    }

    @Override
    public List<Product> findByName(String name) {
        QProduct product = QProduct.product;

        List<Product> products = from(product)
                .where(product.name.eq(name))
                .select(product)
                .fetch();

        return products;
    }
}
