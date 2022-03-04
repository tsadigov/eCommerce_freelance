package com.project.ecommerce.controller;

import com.project.ecommerce.domain.Product;
import com.project.ecommerce.dto.ProductDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    ProductDTO getOne(@PathVariable Long id){
        return productService.getOne(id);
    }

    @GetMapping
    List<ProductDTO> getAll(){

        List<ProductDTO> products = productService.getAll();
        return products;

    }

    @PostMapping
    ResponseEntity<ResponseDTO> create(@RequestBody ProductDTO productDTO){
        ResponseDTO responseDTO = productService.create(productDTO);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

}
