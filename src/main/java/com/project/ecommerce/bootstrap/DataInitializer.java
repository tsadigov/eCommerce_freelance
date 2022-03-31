package com.project.ecommerce.bootstrap;

import com.project.ecommerce.configuration.PasswordConfig;
import com.project.ecommerce.dao.*;
import com.project.ecommerce.domain.*;
import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.dto.SubCategoryDTO;
import com.project.ecommerce.service.RoleService;
import com.project.ecommerce.service.SubCategoryService;
import com.project.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.project.ecommerce.bootstrap.Constants.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {


    private final UserService service;
    private final RoleService roleService;
    private final StoreRepo storeRepo;
    private final AppUserRepo userRepo;
    private final SellerDetailsRepo sellerDetailsRepo;
    private final PasswordEncoder passwordEncoder;
    private final SubCategoryService subCategoryService;
    private final SubcategoryRepo subcategoryRepo;
    private final ProductRepo productRepo;

    @Override
    public void run(String ...args) throws Exception {

        // create path for images if not exists
        String path = "./uploads/profile";
        String pathProduct = "./uploads/product";
        File pathAsFile = new File(path);
        File pathProductAsFile = new File(pathProduct);
        if (!pathAsFile.exists()) {
            pathAsFile.mkdirs();
        }
        if (!pathProductAsFile.exists()) {
            pathProductAsFile.mkdirs();
        }

        Store store1 = new Store(null, "Store 1", null, null);
        storeRepo.save(store1);

        SubCategoryDTO subCategoryDTO1 = new SubCategoryDTO(null,"Man", "Clothes");
        SubCategoryDTO subCategoryDTO2 = new SubCategoryDTO(null, "Woman", "Clothes");
        subCategoryService.create(subCategoryDTO1);
        subCategoryService.create(subCategoryDTO2);

        log.info("Categories saved");

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
        service.addRoleToUser(user.getUsername(),ROLE_ADMIN);
        log.info("Created default admin user");

        AppUser customer = AppUser.builder()
                .id(null)
                .username("customer")
                .email("customer@gmail.com")
                .phoneNumber("051")
                .firstName("Customer")
                .lastName("Test")
                .country("USA")
                .city("LA")
                .password(passwordEncoder.encode("123Aaa"))
                .build();

        userRepo.save(customer);
        service.addRoleToUser("customer",ROLE_CUSTOMER);
        log.info("Created default customer user");

        AppUser seller = AppUser.builder()
                .id(null)
                .username("seller")
                .email("seller@gmail.com")
                .phoneNumber("051")
                .firstName("Seller")
                .lastName("Test")
                .country("Russia")
                .city("Peterburq")
                .password(passwordEncoder.encode("123Aaa"))
                .build();

        userRepo.save(seller);
        service.addRoleToUser("seller",ROLE_SELLER);

        SellerDetails sellerDetails = new SellerDetails(null, "0100", "Baku", 123.211f, seller, store1);
        sellerDetailsRepo.save(sellerDetails);

        log.info("Created default seller user");

        Product product1 = Product.builder()
                .name("Jeans")
                .amount(12L)
                .cost(56.5f)
                .details("100% Cotton")
                .store(store1)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Man"))
                .build();

        Product product2 = Product.builder()
                .name("Hat")
                .amount(12L)
                .cost(26.5f)
                .details("Last model")
                .store(store1)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Woman"))
                .build();

        Product product3 = Product.builder()
                .name("Shoe")
                .amount(12L)
                .cost(25.5f)
                .details("Last model")
                .store(store1)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Woman"))
                .build();

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);

        productRepo.saveAll(products);

    }
}
