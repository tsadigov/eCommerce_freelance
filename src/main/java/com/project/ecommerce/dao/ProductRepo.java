package com.project.ecommerce.dao;

import com.project.ecommerce.domain.Product;
import com.project.ecommerce.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {

    List<Product> findProductByStoreId(Long id);

    List<Product> findProductByNameContainsOrDetailsContains(String searchString, String search);

}
