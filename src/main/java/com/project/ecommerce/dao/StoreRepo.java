package com.project.ecommerce.dao;

import com.project.ecommerce.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepo extends JpaRepository<Store, Long> {
    Store findByStoreName(String name);
}
