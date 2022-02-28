package com.project.ecommerce.dao;

import com.project.ecommerce.domain.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepo extends JpaRepository<AppUser,Long> {
    AppUser findByUsername(String username);
}
