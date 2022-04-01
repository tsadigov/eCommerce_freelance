package com.project.ecommerce.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {

    private Long id;
    private int status;

}
