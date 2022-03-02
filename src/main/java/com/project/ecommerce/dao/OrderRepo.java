package com.project.ecommerce.dao;

import com.project.ecommerce.domain.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Orders, Long> {
}
