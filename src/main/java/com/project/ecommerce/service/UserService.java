package com.project.ecommerce.service;

import com.project.ecommerce.domain.AppUser;
import com.project.ecommerce.dto.CustomerSignUpDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SellerSignUpDTO;

public interface UserService {

    AppUser saveUser(AppUser user);

    AppUser findByUsername(String username);

    AppUser findByEmail(String email);

    void addRoleToUser(String username, String roleName);

    ResponseDTO customerSignUp(CustomerSignUpDTO customerSignUpDTO);

    ResponseDTO sellerSignUp(SellerSignUpDTO sellerSignUpDTO);

}
