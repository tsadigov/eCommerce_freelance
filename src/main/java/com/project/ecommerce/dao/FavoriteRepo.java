package com.project.ecommerce.dao;

import com.project.ecommerce.domain.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepo extends JpaRepository<Favorite, Long> {
}
