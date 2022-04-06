package com.project.ecommerce.dao;

import com.project.ecommerce.domain.Orders;
import com.project.ecommerce.dto.BasketProductDTO;
import com.project.ecommerce.dto.OrderDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders, Long> {

    @Query(nativeQuery = true)
    List<OrderDTO> getAllByUsername(String username);

}
