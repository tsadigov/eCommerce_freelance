package com.project.ecommerce.service;

import com.project.ecommerce.dao.*;
import com.project.ecommerce.domain.*;
import com.project.ecommerce.dto.CustomerSignUpDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SellerSignUpDTO;
import com.project.ecommerce.dto.SignUpDTO;
import com.project.ecommerce.exception.AlreadyExistException;
import com.project.ecommerce.exception.ResourceNotFoundException;
import com.project.ecommerce.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Map;

import static com.project.ecommerce.bootstrap.Constants.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final AppUserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final CustomerDetailsRepo customerDetailsRepo;
    private final SellerDetailsRepo sellerDetailsRepo;
    private final StoreRepo storeRepo;

    @Override
    @Transactional
    public void signUp(SignUpDTO signUpDTO) {

        if (findByUsername(signUpDTO.getUsername()) != null)
            throw new AlreadyExistException(ALREADY_EXISTS);

        // add store
        String storeName = signUpDTO.getStoreName();
        Store store;
        if (storeRepo.findByName(storeName) != null) {
            throw new AlreadyExistException("Store with the name "+ storeName + " already exists");
        }
        else {
            store = new Store();
            store.setName(storeName);
            storeRepo.save(store);
        }


        // add user
        AppUser user = new AppUser();
        user.setUsername(signUpDTO.getUsername());
        user.setEmail(signUpDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));
        userRepo.save(user);

        Role role;
        // get role
        if (!signUpDTO.isCustomer())
            role = roleRepo.findRoleByRoleName(ROLE_SELLER);
        else
            role = roleRepo.findRoleByRoleName(ROLE_CUSTOMER);

        addRoleToUser(user.getUsername(), role.getRoleName());

        // save employee
        CustomerDetails userDetails = new CustomerDetails();
//        userDetails.setFirstName(signUpDTO.getFirstName());
//        userDetails.setLastName(signUpDTO.getLastName());
//        userDetails.setPhoneNumber(signUpDTO.getPhoneNumber());
//        userDetails.setPostalCode(signUpDTO.getPostalCode());
//        userDetails.setAddress(signUpDTO.getAddress());
//        userDetails.setCountry(signUpDTO.getCountry());
//        userDetails.setCity(signUpDTO.getCity());
//        userDetails.setUserFk(user);
//        userDetails.setStore(store);
        customerDetailsRepo.save(userDetails);
    }

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
        }catch (Exception ex){
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
        if(findByUsername(sellerSignUpDTO.getUsername()) != null || findByEmail(sellerSignUpDTO.getEmail()) != null)
            throw new AlreadyExistException(ALREADY_EXISTS);

        log.info(String.valueOf(sellerSignUpDTO));

        ResponseDTO responseDTO = null;
        Role role = roleRepo.findRoleByRoleName(ROLE_SELLER);

        try {
            AppUser user = Mapper.map(sellerSignUpDTO, AppUser.class);
            user.setPassword(passwordEncoder.encode(sellerSignUpDTO.getPassword()));
            userRepo.save(user);

            addRoleToUser(user.getUsername(), role.getRoleName());

            Store store = Mapper.map(sellerSignUpDTO, Store.class);
            storeRepo.save(store);

            SellerDetails sellerDetails = Mapper.map(sellerSignUpDTO, SellerDetails.class);
            sellerDetails.setUser(user);
            sellerDetails.setStore(store);
            sellerDetailsRepo.save(sellerDetails);

            responseDTO = ResponseDTO.builder()
                    .code(CREATED_CODE)
                    .message(CREATED)
                    .build();
        }catch (Exception ex){
            responseDTO = ResponseDTO.builder()
                    .code(INTERNAL_SERVER_ERROR_CODE)
                    .message(BAD_CREDENTIALS)
                    .build();
            log.info(ex.getMessage());
        }
        return responseDTO;
    }

    @Override
    public AppUser saveUser(AppUser user) {
        log.info("Saving new USER {} to the DB", user.getUsername());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        log.info("Adding rol {} to user {}", username, roleName);
        AppUser user = userRepo.findByUsername(username);
        Role role = roleRepo.findRoleByRoleName(roleName);

        List<Role> roles = new ArrayList<>();
        roles.add(role);
        user.setRoles(roles);
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

    @Override
    public AppUser findByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    @Override
    public AppUser findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

}
