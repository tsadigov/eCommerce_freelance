package com.project.ecommerce.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SellerSignUpDTO {

    private String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String password;
    private String prilePictureUrl;
    private String postalCode;
    private String address;
    private Float balance;
    private String storeName;
    private String storeUrl;
    private String storeLogoUrl;

}
