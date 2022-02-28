package com.project.ecommerce.service;

import com.project.ecommerce.dao.AppUserDetailsRepo;
import com.project.ecommerce.dao.AppUserRepo;
import com.project.ecommerce.dao.RoleRepo;
import com.project.ecommerce.dao.StoreRepo;
import com.project.ecommerce.domain.AppUser;
import com.project.ecommerce.domain.AppUserDetails;
import com.project.ecommerce.domain.Role;
import com.project.ecommerce.domain.Store;
import com.project.ecommerce.dto.SignUpDTO;
import com.project.ecommerce.exception.AlreadyExistException;
import com.project.ecommerce.exception.ResourceNotFoundException;
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

import static com.project.ecommerce.bootstrap.Constants.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final AppUserRepo userRepo;
    private final RoleRepo roleRepo;
    private final PasswordEncoder passwordEncoder;
    private final AppUserDetailsRepo employeeRepo;
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


        // add admin user
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
        AppUserDetails userDetails = new AppUserDetails();
        userDetails.setFirstName(signUpDTO.getFirstName());
        userDetails.setLastName(signUpDTO.getLastName());
        userDetails.setPhoneNumber(signUpDTO.getPhoneNumber());
        userDetails.setPostalCode(signUpDTO.getPostalCode());
        userDetails.setAddress(signUpDTO.getAddress());
        userDetails.setCountry(signUpDTO.getCountry());
        userDetails.setCity(signUpDTO.getCity());
        userDetails.setUserFk(user);
        userDetails.setStore(store);
        employeeRepo.save(userDetails);
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

}
