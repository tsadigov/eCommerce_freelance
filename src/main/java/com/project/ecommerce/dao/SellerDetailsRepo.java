package com.project.ecommerce.dao;

import com.project.ecommerce.domain.AppUser;
import com.project.ecommerce.domain.SellerDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellerDetailsRepo extends JpaRepository<SellerDetails, Long> {
    SellerDetails findByUser(AppUser user);
}
