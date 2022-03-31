package com.project.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketProductDTO {

    private Long id;
    private Long productId;
    private Long basketAmount;
    private Long productAmount;
    private String productName;
    private String productDetails;
    private String productPhotoUrl;
    private Float cost;
    private String storeName;
    private String username;

}
