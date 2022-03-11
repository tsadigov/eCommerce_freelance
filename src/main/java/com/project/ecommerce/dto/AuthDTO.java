package com.project.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthDTO {

    private String accessToken;
    private String refreshToken;

}
