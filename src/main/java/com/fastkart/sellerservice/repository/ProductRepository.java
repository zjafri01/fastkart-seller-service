package com.fastkart.sellerservice.repository;

import com.fastkart.sellerservice.model.Product;
import com.fastkart.sellerservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    public Product save(Product product);

    //public Product findProductBy(Product product);

    public List<Product> findProductByUserId(Long id);
}