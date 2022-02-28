package com.project.ecommerce.service;

import com.project.ecommerce.domain.AppUser;
import com.project.ecommerce.dto.SignUpDTO;

public interface UserService {

    AppUser saveUser(AppUser user);

    AppUser findByUsername(String username);

    void addRoleToUser(String username, String roleName);

    void signUp(SignUpDTO signUpDTO);

}
