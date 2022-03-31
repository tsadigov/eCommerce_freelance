package com.project.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketProductCreationDTO {

    private Long amount;
    private Long productId;
    private String username;

}
