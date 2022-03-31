package com.project.ecommerce.controller;

import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SubCategoryDTO;
import com.project.ecommerce.service.CategoryService;
import com.project.ecommerce.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
public class CategoryController {

    private final CategoryService service;

    @GetMapping("/{id}")
    ResponseEntity<ResponseDTO> getOne(@PathVariable Long id) {
        ResponseDTO responseDTO = service.getOne(id);
        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @GetMapping
    ResponseEntity<ResponseDTO> getAll() {
        ResponseDTO responseDTO = service.getAll();
        return ResponseEntity.ok()
                .body(responseDTO);
    }

}
