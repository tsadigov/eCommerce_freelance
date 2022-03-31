package com.project.ecommerce.dto;

import lombok.Data;

@Data
public class CustomerDTO {

    private String username;
    private String email;
    private String phoneNumber;
    private String firstName;
    private String lastName;
    private String country;
    private String city;
    private String profilePictureUrl;

}
