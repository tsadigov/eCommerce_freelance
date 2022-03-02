package com.project.ecommerce.dao;

import com.project.ecommerce.domain.Subcategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubcategoryRepo extends JpaRepository<Subcategory, Long> {
}
