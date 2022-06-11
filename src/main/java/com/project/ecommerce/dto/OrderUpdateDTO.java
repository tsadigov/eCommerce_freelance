package com.project.ecommerce.dto;

import com.project.ecommerce.domain.OrderStatus;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderUpdateDTO {

    private Long id;
    private OrderStatus status;

}
