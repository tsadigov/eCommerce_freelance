package com.project.ecommerce.bootstrap;

import com.project.ecommerce.configuration.PasswordConfig;
import com.project.ecommerce.dao.AppUserRepo;
import com.project.ecommerce.dao.StoreRepo;
import com.project.ecommerce.domain.AppUser;
import com.project.ecommerce.domain.Role;
import com.project.ecommerce.domain.Store;
import com.project.ecommerce.service.RoleService;
import com.project.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

import static com.project.ecommerce.bootstrap.Constants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {


    private final UserService service;
    private final RoleService roleService;
    private final StoreRepo storeRepo;
    private final AppUserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
//    private final

    @Override
    public void run(String ...args) throws Exception {

//        storeRepo.save(new Store(null, "Store 1"));
//        storeRepo.save(new Store(null, "Store 2"));
//        storeRepo.save(new Store(null, "Store 3"));

        log.info("stores saved");

        roleService.saveRole(new Role(null, ROLE_ADMIN));
        roleService.saveRole(new Role(null, ROLE_SELLER));
        roleService.saveRole(new Role(null, ROLE_CUSTOMER));
        log.info("Added roles to db");

        AppUser user = AppUser.builder()
                .id(null)
                .username("john")
                .email("john@gmail.com")
                .phoneNumber("051")
                .firstName("John")
                .lastName("Doe")
                .country("Some country")
                .city("Some city")
                .password(passwordEncoder.encode("123Aaa"))
                .build();

        userRepo.save(user);
        service.addRoleToUser("john",ROLE_ADMIN);
        log.info("Created default admin user");

    }
}
