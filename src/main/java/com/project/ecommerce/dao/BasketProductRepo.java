package com.project.ecommerce.dao;

import com.project.ecommerce.domain.BasketProduct;
import com.project.ecommerce.dto.BasketProductDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BasketProductRepo extends JpaRepository<BasketProduct, Long> {

//    @Query(nativeQuery = true,
//            value = """
//                    SELECT bp.id as id, bp.product_id as productId, bp.amount as basketAmount, p.amount as productAmount, p.name as productName, p.details as productDetails, p.photo_url as productPhotoUrl, p.cost as cost, s.store_name as storeName, u.username as username FROM `e-commerce`.basket_product as bp\s
//                    INNER JOIN `e-commerce`.product as p on bp.product_id = p.id
//                    INNER JOIN `e-commerce`.app_user as u on bp.user_id = u.id
//                    INNER JOIN `e-commerce`.store as s on s.id = p.store""")
//    List<BasketProductDTO> getAllByUsername();

    @Query(nativeQuery = true)
    List<BasketProductDTO> getAllByUsername(String username);

}
