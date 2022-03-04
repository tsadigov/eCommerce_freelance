package com.project.ecommerce.service;

import com.project.ecommerce.dto.*;

public interface UserService {

    ResponseDTO getCustomer(String username);

    ResponseDTO getSeller(String username);

    void addRoleToUser(String username, String roleName);

    ResponseDTO customerSignUp(CustomerSignUpDTO customerSignUpDTO);

    ResponseDTO sellerSignUp(SellerSignUpDTO sellerSignUpDTO);

    ResponseDTO updateCustomerProfile(CustomerDTO customerSignUpDTO);

    ResponseDTO updateSellerProfile(SellerDTO sellerDTO);

}
