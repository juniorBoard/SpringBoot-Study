package com.example.sort_paging.repository;

import com.example.sort_paging.domain.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByNameContains(String keyword);
    List<Product> findByPriceGreaterThanEqual(int min);
    List<Product> findByCategoryContainsAndPriceGreaterThan(String category, int min);
    List<Product> findAll();
    List<Product> findAllByOrderByPriceAsc();
    List<Product> findByNameContainsOrderByPriceAsc(String name);
    List<Product> findByNameContains(String name, Sort sort);
    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product AS p WHERE p.category = ?1")
    List<Product> findByCategory(String category);
    @Query("SELECT p FROM Product p WHERE p.category LIKE %:category%")
    List<Product> findByCategory2(@Param("category") String category);
    @Query("SELECT p.name, p.category, p.price FROM Product p WHERE p.category LIKE %:category%")
    List<Object[]> findByCategory3(@Param("category") String category);
}
