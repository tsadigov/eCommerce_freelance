package com.project.ecommerce.service;

import com.project.ecommerce.domain.Category;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SubCategoryDTO;


public interface CategoryService {

    ResponseDTO getOne(Long id);

    ResponseDTO getAll();

}
