package com.project.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketProductUpdateDTO {

    private Long id;
    private Long amount;

}
