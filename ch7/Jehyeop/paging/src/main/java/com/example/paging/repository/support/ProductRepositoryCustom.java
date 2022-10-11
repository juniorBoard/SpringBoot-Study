package com.example.paging.repository.support;

import com.example.paging.entity.Product;

import java.util.List;

public interface ProductRepositoryCustom {

    List<Product> findByName(String name);
}
