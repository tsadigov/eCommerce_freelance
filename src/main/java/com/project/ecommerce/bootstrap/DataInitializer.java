package com.project.ecommerce.bootstrap;

import com.project.ecommerce.dao.*;
import com.project.ecommerce.domain.*;
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
        Store store2 = new Store(null, "Store 2", null, null);
        storeRepo.save(store2);

        SubCategoryDTO subCategoryDTO1 = new SubCategoryDTO(null,"Kişi", "Geyim");
        subCategoryService.create(subCategoryDTO1);
        SubCategoryDTO subCategoryDTO2 = new SubCategoryDTO(null, "Qadın", "Geyim");
        subCategoryService.create(subCategoryDTO2);
        SubCategoryDTO subCategoryDTO3 = new SubCategoryDTO(null, "Uşaq", "Geyim");
        subCategoryService.create(subCategoryDTO3);
        SubCategoryDTO subCategoryDTO4 = new SubCategoryDTO(null, "Telefonlar", "Elektronika");
        subCategoryService.create(subCategoryDTO4);
        SubCategoryDTO subCategoryDTO5 = new SubCategoryDTO(null, "Kompüterlər", "Elektronika");
        subCategoryService.create(subCategoryDTO5);
        SubCategoryDTO subCategoryDTO6 = new SubCategoryDTO(null, "Aksesuarlar", "Elektronika");
        subCategoryService.create(subCategoryDTO6);
        SubCategoryDTO subCategoryDTO7 = new SubCategoryDTO(null, "Printerlər", "Elektronika");
        subCategoryService.create(subCategoryDTO7);
        SubCategoryDTO subCategoryDTO8 = new SubCategoryDTO(null, "Maşın üçün", "Ləvazimatlar");
        subCategoryService.create(subCategoryDTO8);
        SubCategoryDTO subCategoryDTO9 = new SubCategoryDTO(null, "Ev üçün", "Ləvazimatlar");
        subCategoryService.create(subCategoryDTO9);
        SubCategoryDTO subCategoryDTO10 = new SubCategoryDTO(null, "Bağ üçün", "Ləvazimatlar");
        subCategoryService.create(subCategoryDTO10);

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

        AppUser seller2 = AppUser.builder()
                .id(null)
                .username("seller2")
                .email("seller2@gmail.com")
                .phoneNumber("051")
                .firstName("Seller")
                .lastName("Test")
                .country("Russia")
                .city("Peterburq")
                .password(passwordEncoder.encode("123Aaa"))
                .build();

        userRepo.save(seller);
        userRepo.save(seller2);
        service.addRoleToUser("seller",ROLE_SELLER);
        service.addRoleToUser("seller2",ROLE_SELLER);

        SellerDetails sellerDetails = new SellerDetails(null, "0100", "Baku", 123.211f, seller, store1);
        sellerDetailsRepo.save(sellerDetails);
        SellerDetails sellerDetails2 = new SellerDetails(null, "0101", "Baku", 123.211f, seller2, store2);
        sellerDetailsRepo.save(sellerDetails2);

        log.info("Created default seller user");

        Product product1 = Product.builder()
                .name("Jeans")
                .amount(12L)
                .cost(56.5f)
                .details("100% Pambıq")
                .store(store1)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Kişi"))
                .build();

        Product product2 = Product.builder()
                .name("Papaq")
                .amount(12L)
                .cost(26.5f)
                .details("Sonuncu model")
                .store(store2)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Qadın"))
                .build();

        Product product3 = Product.builder()
                .name("Ayaqqabı")
                .amount(12L)
                .cost(53.5f)
                .details("Sonuncu model")
                .store(store1)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Uşaq"))
                .build();

        Product product4 = Product.builder()
                .name("Samsung Galaxy S22")
                .amount(12L)
                .cost(500.0f)
                .details("Samsung Galaxy A53 DS 256GB 5G White")
                .store(store1)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Telefonlar"))
                .build();

        Product product5 = Product.builder()
                .name("Apple Macbook Pro")
                .amount(12L)
                .cost(3000.0f)
                .details("Macbook Pro, 13.3 inç, 256GB SSD, M1 Prosessor")
                .store(store1)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Kompüterlər"))
                .build();

        Product product6 = Product.builder()
                .name("JBL Qulaqcıq")
                .amount(12L)
                .cost(125.5f)
                .details("JBL JR 460NC Blue,750mAh, 30 saat, 200qr")
                .store(store2)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Aksesuarlar"))
                .build();

        Product product7 = Product.builder()
                .name("Epson L805")
                .amount(12L)
                .cost(625.5f)
                .details("İnkjet, 6 rəngli, yüksək keyfiyyətli çağ qabiliyyətinə malik printer")
                .store(store2)
                .subcategory(subcategoryRepo.findSubcategoryBySubCategoryName("Printerlər"))
                .build();

        List<Product> products = new ArrayList<>();
        products.add(product1);
        products.add(product2);
        products.add(product3);
        products.add(product4);
        products.add(product5);
        products.add(product6);
        products.add(product7);

        productRepo.saveAll(products);

    }
}
