package com.project.ecommerce.service;

import com.project.ecommerce.dto.CustomerDTO;
import com.project.ecommerce.dto.CustomerSignUpDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SellerSignUpDTO;

public interface UserService {

    ResponseDTO getCustomer(String username);

    void addRoleToUser(String username, String roleName);

    ResponseDTO customerSignUp(CustomerSignUpDTO customerSignUpDTO);

    ResponseDTO sellerSignUp(SellerSignUpDTO sellerSignUpDTO);

    ResponseDTO updateCustomerProfile(CustomerDTO customerSignUpDTO);

    ResponseDTO updateSellerProfile(SellerSignUpDTO sellerSignUpDTO);

}
