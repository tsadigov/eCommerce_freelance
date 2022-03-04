package com.project.ecommerce.service;

import com.project.ecommerce.domain.Product;
import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.dto.ResponseDTO;

import java.util.List;

public interface ProductService {

    ProductDTO getOne(Long id);

    List<ProductDTO> getAll();

    ResponseDTO create(ProductDTO productDTO);

}
