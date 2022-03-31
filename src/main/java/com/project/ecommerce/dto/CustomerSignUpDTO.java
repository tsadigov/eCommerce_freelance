package com.project.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerSignUpDTO {

    private String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String password;

}
