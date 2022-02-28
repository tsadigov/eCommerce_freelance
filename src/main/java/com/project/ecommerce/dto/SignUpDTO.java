package com.project.ecommerce.dto;

import com.project.ecommerce.utils.Validator;
import lombok.Data;
import org.springframework.security.authentication.BadCredentialsException;

import static com.project.ecommerce.bootstrap.Constants.WRONG_EMAIL_FORMAT;
import static com.project.ecommerce.bootstrap.Constants.WRONG_PASSWORD_FORMAT;

@Data
public class SignUpDTO {

    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String postalCode;
    private String address;
    private String country;
    private String city;
    private String storeName;
    private String username;
    private String email;
    private String password;
    private boolean isCustomer;

    public String getEmail() {
        if (Validator.isValidEmailFormat(email)) return email;
        else throw new BadCredentialsException(WRONG_EMAIL_FORMAT);
    }

    public String getPassword() {
        if (Validator.isValidPasswordFormat(password)) return password;
        else throw new BadCredentialsException(WRONG_PASSWORD_FORMAT);
    }
}
