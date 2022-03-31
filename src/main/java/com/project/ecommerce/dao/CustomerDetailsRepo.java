package com.project.ecommerce.dao;

import com.project.ecommerce.domain.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerDetailsRepo extends JpaRepository<CustomerDetails, Long> {
}
