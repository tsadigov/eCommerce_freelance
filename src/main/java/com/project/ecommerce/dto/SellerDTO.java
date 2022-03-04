package com.project.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SellerDTO {

    private String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String profilePictureUrl;
    private String postalCode;
    private String address;

}
