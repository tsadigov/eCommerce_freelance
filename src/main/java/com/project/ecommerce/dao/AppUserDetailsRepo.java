package com.project.ecommerce.dao;

import com.project.ecommerce.domain.AppUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserDetailsRepo extends JpaRepository<AppUserDetails, Long> {
}
