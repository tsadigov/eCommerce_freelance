package com.project.ecommerce.service;

import com.project.ecommerce.dao.*;
import com.project.ecommerce.domain.*;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.exception.AlreadyExistException;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.project.ecommerce.bootstrap.Constants.*;

@Service
//@Transactional
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final AppUserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDetailsRepo customerDetailsRepo;
    private final SellerDetailsRepo sellerDetailsRepo;
    private final StoreRepo storeRepo;

    @Transactional
    @Override
    public ResponseDTO customerSignUp(CustomerSignUpDTO customerSignUpDTO) {
        if (findByUsername(customerSignUpDTO.getUsername()) != null || findByEmail(customerSignUpDTO.getEmail()) != null)
            throw new AlreadyExistException(ALREADY_EXISTS);

        ResponseDTO responseDTO = null;
        Role role = roleRepo.findRoleByRoleName(ROLE_CUSTOMER);
        try {
            AppUser user = Mapper.map(customerSignUpDTO, AppUser.class);
            user.setPassword(passwordEncoder.encode(customerSignUpDTO.getPassword()));
            userRepo.save(user);

            addRoleToUser(user.getUsername(), role.getRoleName());

            responseDTO = ResponseDTO.builder()
                    .code(CREATED_CODE)
                    .message(CREATED)
                    .build();
        } catch (Exception ex) {
            responseDTO = ResponseDTO.builder()
                    .code(INTERNAL_SERVER_ERROR_CODE)
                    .message(BAD_CREDENTIALS)
                    .build();
            log.info(ex.getMessage());
        }
        return responseDTO;
    }

    @Transactional
    @Override
    public ResponseDTO sellerSignUp(SellerSignUpDTO sellerSignUpDTO) {
        if (findByUsername(sellerSignUpDTO.getUsername()) != null || findByEmail(sellerSignUpDTO.getEmail()) != null)
            throw new AlreadyExistException(ALREADY_EXISTS);

        log.info(String.valueOf(sellerSignUpDTO));

        ResponseDTO responseDTO = null;
        Role role = roleRepo.findRoleByRoleName(ROLE_SELLER);

        try {
            // Save user
            AppUser user = Mapper.map(sellerSignUpDTO, AppUser.class);
            user.setPassword(passwordEncoder.encode(sellerSignUpDTO.getPassword()));
            userRepo.save(user);

            // add role to user
            addRoleToUser(user.getUsername(), role.getRoleName());

            // add store
            Store store = Mapper.map(sellerSignUpDTO, Store.class);
            storeRepo.save(store);

            // add seller details, user and store to seller details
            SellerDetails sellerDetails = Mapper.map(sellerSignUpDTO, SellerDetails.class);
            sellerDetails.setUser(user);
            sellerDetails.setStore(store);
            sellerDetailsRepo.save(sellerDetails);

            responseDTO = ResponseDTO.builder()
                    .code(CREATED_CODE)
                    .message(CREATED)
                    .build();

        } catch (Exception ex) {
            responseDTO = ResponseDTO.builder()
                    .code(INTERNAL_SERVER_ERROR_CODE)
                    .message(BAD_CREDENTIALS)
                    .build();
            log.info(ex.getMessage());
        }
        return responseDTO;
    }

    @Transactional
    @Override
    public ResponseDTO updateCustomerProfile(CustomerDTO customerDTO) {

        ResponseDTO responseDTO;

        try{
            AppUser user = findByUsername(customerDTO.getUsername());
            user.setPhoneNumber(customerDTO.getPhoneNumber());
            user.setFirstName(customerDTO.getFirstName());
            user.setLastName(customerDTO.getLastName());
            user.setCountry(customerDTO.getCountry());
            user.setCity(customerDTO.getCity());
            user.setProfilePictureUrl(customerDTO.getProfilePictureUrl());
            userRepo.save(user);

            responseDTO = ResponseDTO.builder()
                    .code(UPDATED_CODE)
                    .message(UPDATED)
                    .response(customerDTO)
                    .build();

        }catch (Exception ex){
            responseDTO = ResponseDTO.builder()
                    .code(INTERNAL_SERVER_ERROR_CODE)
                    .message(CANNOT_BE_UPDATED)
                    .build();
        }

        return responseDTO;
    }

    @Override
    public ResponseDTO updateSellerProfile(SellerDTO sellerDTO) {
        ResponseDTO responseDTO;

        try{
            AppUser user = findByUsername(sellerDTO.getUsername());
            user.setPhoneNumber(sellerDTO.getPhoneNumber());
            user.setFirstName(sellerDTO.getFirstName());
            user.setLastName(sellerDTO.getLastName());
            user.setCountry(sellerDTO.getCountry());
            user.setCity(sellerDTO.getCity());
            user.setProfilePictureUrl(sellerDTO.getProfilePictureUrl());
            userRepo.save(user);
            log.info("Seller User {} updated", user.getUsername());

            SellerDetails sellerDetails = sellerDetailsRepo.findByUser(user);
            sellerDetails.setPostalCode(sellerDTO.getPostalCode());
            sellerDetails.setAddress(sellerDTO.getAddress());
            sellerDetailsRepo.save(sellerDetails);
            log.info("Sellerdetails for user {} updated", user.getUsername());

            responseDTO = ResponseDTO.builder()
                    .code(UPDATED_CODE)
                    .message(UPDATED)
                    .response(sellerDTO)
                    .build();

        }catch (Exception ex){
            responseDTO = ResponseDTO.builder()
                    .code(INTERNAL_SERVER_ERROR_CODE)
                    .message(CANNOT_BE_UPDATED)
                    .build();
        }

        return responseDTO;
    }

    @Override
    public ResponseDTO getCustomer(String username) {

        ResponseDTO responseDTO;

        try {
            AppUser user = findByUsername(username);
            CustomerDTO customerDTO = Mapper.map(user, CustomerDTO.class);

            responseDTO = ResponseDTO.builder()
                    .code(SUCCESS_CODE)
                    .response(customerDTO)
                    .build();

        } catch (Exception ex) {
            responseDTO = ResponseDTO.builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND_MESSAGE)
                    .build();
        }


        return responseDTO;
    }

    @Override
    public ResponseDTO getSeller(String username) {

        ResponseDTO responseDTO;

        try {
            AppUser user = findByUsername(username);
            SellerDetails sellerDetails = sellerDetailsRepo.findByUser(user);
            SellerDTO sellerDTO = SellerDTO.builder()
                    .username(username)
                    .email(user.getEmail())
                    .phoneNumber(user.getPhoneNumber())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .country(user.getCountry())
                    .city(user.getCity())
                    .profilePictureUrl(user.getProfilePictureUrl())
                    .postalCode(sellerDetails.getPostalCode())
                    .address(sellerDetails.getAddress())
                    .build();

            responseDTO = ResponseDTO.builder()
                    .code(SUCCESS_CODE)
                    .response(sellerDTO)
                    .build();

        } catch (Exception ex) {
            responseDTO = ResponseDTO.builder()
                    .code(NOT_FOUND_CODE)
                    .message(NOT_FOUND_MESSAGE)
                    .build();
        }


        return responseDTO;
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding rol {} to user {}", username, roleName);
        AppUser user = userRepo.findByUsername(username);
        Role role = roleRepo.findRoleByRoleName(roleName);

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findByUsername(username);
        if (user == null) {
            log.info("User not found in the database");
            throw new ResourceNotFoundException(USER_NOT_FOUND);
        } else {
            log.info("User found in the database");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return new User(user.getUsername(), user.getPassword(), authorities);
    }

    public AppUser findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    public AppUser findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
