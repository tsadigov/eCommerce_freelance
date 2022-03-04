package com.project.ecommerce.service;

import com.project.ecommerce.dao.ProductPhotoRepo;
import com.project.ecommerce.dao.ProductRepo;
import com.project.ecommerce.dao.StoreRepo;
import com.project.ecommerce.dao.SubcategoryRepo;
import com.project.ecommerce.domain.Product;
import com.project.ecommerce.domain.ProductPhoto;
import com.project.ecommerce.domain.Store;
import com.project.ecommerce.domain.Subcategory;
import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.project.ecommerce.bootstrap.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepo productRepo;
    private final SubcategoryRepo subcategoryRepo;
    private final StoreRepo storeRepo;
    private final ProductPhotoRepo productPhotoRepo;


    @Override
    public ProductDTO getOne(Long id) {

        Product product = productRepo.getById(id);

        ProductDTO productDTO = ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .amount(product.getAmount())
                .cost(product.getCost())
                .details(product.getDetails())
                .subCategoryId(product.getSubcategory().getId())
                .storeId(product.getStore().getId())
                .imageUrl(null)
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
                    .subCategoryId(product.getSubcategory().getId())
                    .storeId(product.getStore().getId())
                    .imageUrl(null)
                    .build();

            productDTOList.add(productDTO);
        }

        return productDTOList;
    }

    @Override
    public ResponseDTO create(ProductDTO productDTO) {

        Subcategory subcategory = subcategoryRepo.getById(productDTO.getSubCategoryId());
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

        Set<ProductPhoto> photos = new HashSet<>();
        photos.add(new ProductPhoto(null, "image1.png", product));
        photos.add(new ProductPhoto(null, "image2.png", product));
        photos.add(new ProductPhoto(null, "image3.png", product));

        productPhotoRepo.saveAll(photos);

        Product tempProduct = productRepo.getById(product.getId());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(CREATED_CODE)
                .message(CREATED)
                .response(tempProduct)
                .build();

        return responseDTO;
    }
}
