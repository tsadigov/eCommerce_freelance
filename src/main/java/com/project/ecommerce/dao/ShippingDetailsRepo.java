package com.project.ecommerce.dao;

import com.project.ecommerce.domain.ShippingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingDetailsRepo extends JpaRepository<ShippingDetails, Long> {
}
