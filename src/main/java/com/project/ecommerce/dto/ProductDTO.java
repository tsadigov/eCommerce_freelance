package com.project.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    private String name;
    private Long amount;
    private Float cost;
    private String details;
    private Long subCategoryId;
    private Long storeId;
    private String imageUrl;

}
