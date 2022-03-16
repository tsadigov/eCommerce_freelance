package com.project.ecommerce.dao;

import com.project.ecommerce.domain.Product;
import com.project.ecommerce.dto.ProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
