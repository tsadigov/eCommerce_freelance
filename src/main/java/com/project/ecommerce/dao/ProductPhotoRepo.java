package com.project.ecommerce.dao;

import com.project.ecommerce.domain.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPhotoRepo extends JpaRepository<ProductPhoto, Long> {
}
