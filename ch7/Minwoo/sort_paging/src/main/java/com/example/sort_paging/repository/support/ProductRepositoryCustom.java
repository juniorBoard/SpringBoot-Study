package com.example.sort_paging.repository.support;

import com.example.sort_paging.domain.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    List<Product> findByName(String name);
}
