package com.project.ecommerce.service;

import com.project.ecommerce.dao.ProductRepo;
import com.project.ecommerce.dao.StoreRepo;
import com.project.ecommerce.dao.SubcategoryRepo;
import com.project.ecommerce.domain.Product;
import com.project.ecommerce.domain.Store;
import com.project.ecommerce.domain.Subcategory;
import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

import static com.project.ecommerce.bootstrap.Constants.*;
import static java.nio.file.Files.copy;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final SubcategoryRepo subcategoryRepo;
    private final StoreRepo storeRepo;
//    private final ProductPhotoRepo productPhotoRepo;


    @Override
    public ProductDTO getOne(Long id) {

        Product product = productRepo.getById(id);
        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .amount(product.getAmount())
                .cost(product.getCost())
                .details(product.getDetails())
                .subcategoryId(product.getSubcategory().getId())
                .storeId(product.getStore().getId())
                .photoUrl(product.getPhotoUrl())
                .build();
        return productDTO;
    }

    @Override
    public List<ProductDTO> getAll() {
        List<Product> products = productRepo.findAll();

        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .amount(product.getAmount())
                    .cost(product.getCost())
                    .details(product.getDetails())
                    .subcategoryId(product.getSubcategory().getId())
                    .storeId(product.getStore().getId())
                    .photoUrl(product.getPhotoUrl())
                    .build();

            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    @Override
    public List<ProductDTO> getAllByStoreId(Long storeId) {
        List<Product> products = productRepo.findProductByStoreId(storeId);

        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            ProductDTO productDTO = ProductDTO.builder()
                    .id(product.getId())
                    .name(product.getName())
                    .amount(product.getAmount())
                    .cost(product.getCost())
                    .details(product.getDetails())
                    .subcategoryId(product.getSubcategory().getId())
                    .storeId(product.getStore().getId())
                    .photoUrl(product.getPhotoUrl())
                    .build();

            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    @Override
    public ResponseDTO create(ProductDTO productDTO) {

        Subcategory subcategory = subcategoryRepo.getById(productDTO.getSubcategoryId());
        Store store = storeRepo.getById(productDTO.getStoreId());

        Product product = Product.builder()
                .name(productDTO.getName())
                .amount(productDTO.getAmount())
                .cost(productDTO.getCost())
                .details(productDTO.getDetails())
                .store(store)
                .subcategory(subcategory)
                .build();

        productRepo.save(product);

//        Set<ProductPhoto> photos = new HashSet<>();
//        photos.add(new ProductPhoto(null, "image1.png", product));
//        photos.add(new ProductPhoto(null, "image2.png", product));
//        photos.add(new ProductPhoto(null, "image3.png", product));
//
//        productPhotoRepo.saveAll(photos);

        Product tempProduct = productRepo.getById(product.getId());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(CREATED_CODE)
                .message(CREATED)
                .response(tempProduct)
                .build();

        return responseDTO;
    }

    @Override
    public ResponseDTO update(MultipartFile file, ProductDTO productDTO) throws IOException{
        String fileName = StringUtils.cleanPath(System.currentTimeMillis()+PRODUCT_PHOTO_END);
        Path fileStorage = get(DIRECTORY_PRODUCT, fileName).toAbsolutePath().normalize();
        copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);

        Subcategory subcategory = subcategoryRepo.getById(productDTO.getSubcategoryId());
        Store store = storeRepo.getById(productDTO.getStoreId());

        Product product = Product.builder()
                .id(productDTO.getId())
                .name(productDTO.getName())
                .amount(productDTO.getAmount())
                .cost(productDTO.getCost())
                .details(productDTO.getDetails())
                .store(store)
                .subcategory(subcategory)
                .photoUrl(fileName)
                .build();

        productRepo.save(product);

        ProductDTO tempProduct = Mapper.map(productRepo.getById(product.getId()),ProductDTO.class);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(UPDATED_CODE)
                .message(UPDATED)
                .response(tempProduct)
                .build();

        return responseDTO;
    }

    @Override
    public ResponseDTO delete(Long id) {
        productRepo.deleteById(id);
        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(DELETED_CODE)
                .message(DELETED)
                .build();
        return responseDTO;
    }

    @Override
    public ResponseDTO createProduct(MultipartFile file, ProductDTO productDTO) throws IOException {

        String fileName = StringUtils.cleanPath(System.currentTimeMillis()+PRODUCT_PHOTO_END);
        Path fileStorage = get(DIRECTORY_PRODUCT, fileName).toAbsolutePath().normalize();
        copy(file.getInputStream(), fileStorage, REPLACE_EXISTING);

        Subcategory subcategory = subcategoryRepo.getById(productDTO.getSubcategoryId());
        Store store = storeRepo.getById(productDTO.getStoreId());

        Product product = Product.builder()
                .name(productDTO.getName())
                .amount(productDTO.getAmount())
                .cost(productDTO.getCost())
                .details(productDTO.getDetails())
                .store(store)
                .subcategory(subcategory)
                .photoUrl(fileName)
                .build();

        productRepo.save(product);

        ProductDTO tempProduct = Mapper.map(productRepo.getById(product.getId()),ProductDTO.class);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(CREATED_CODE)
                .message(CREATED)
                .response(tempProduct)
                .build();

        return responseDTO;
    }
}
