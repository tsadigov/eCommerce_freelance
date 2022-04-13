package com.project.ecommerce.service;

import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.dto.ResponseDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {

    ProductDTO getOne(Long id);

    List<ProductDTO> getAll();

    List<ProductDTO> getAllByStoreId(Long storeId);

    List<ProductDTO> getAllByNameAndDetails(String searchString);

    ResponseDTO create(ProductDTO productDTO);

    ResponseDTO update(MultipartFile file, ProductDTO productDTO) throws IOException;

    ResponseDTO delete(Long id);

    ResponseDTO createProduct(MultipartFile file, ProductDTO productDTO) throws IOException;

}
