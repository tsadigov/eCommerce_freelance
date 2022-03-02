package com.project.ecommerce.dao;

import com.project.ecommerce.domain.BasketProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BasketProductRepo extends JpaRepository<BasketProduct, Long> {
}
