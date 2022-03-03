package com.project.ecommerce.service;

import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SubCategoryDTO;


public interface SubCategoryService {

    ResponseDTO getOne(Long id);

    ResponseDTO getAll();

    ResponseDTO create(SubCategoryDTO subCategoryDTO);

}
