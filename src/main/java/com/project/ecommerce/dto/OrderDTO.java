package com.project.ecommerce.dto;

import com.project.ecommerce.domain.OrderStatus;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {

    private Long id;
    private Long productId;
    private Long orderAmount;
    private String productName;
    private String productDetails;
    private String productPhotoUrl;
    private Float cost;
    private String storeName;
    private String username;
    private String trackNumber;
    private LocalDate orderDate;
    private String orderStatus;

}
