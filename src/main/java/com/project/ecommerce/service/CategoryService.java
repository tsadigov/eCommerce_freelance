package com.project.ecommerce.service;

import com.project.ecommerce.dto.ResponseDTO;


public interface CategoryService {

    ResponseDTO getOne(Long id);

    ResponseDTO getAll();

}
