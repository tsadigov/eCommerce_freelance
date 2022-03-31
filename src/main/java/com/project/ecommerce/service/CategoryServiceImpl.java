package com.project.ecommerce.service;

import com.project.ecommerce.dao.CategoryRepo;
import com.project.ecommerce.dao.SubcategoryRepo;
import com.project.ecommerce.domain.Category;
import com.project.ecommerce.domain.Subcategory;
import com.project.ecommerce.dto.CategoryDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SubCategoryDTO;
import com.project.ecommerce.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.project.ecommerce.bootstrap.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepo categoryRepo;
    private final SubcategoryRepo subcategoryRepo;

    @Override
    public ResponseDTO getOne(Long id) {
        Optional<Category> category = categoryRepo.findById(id);
        log.info("Category: {}", category.get());

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS)
                .response(category.get().getCategoryName())
                .build();

        return responseDTO;
    }

    @Override
    public ResponseDTO getAll() {
        List<Category> categories = categoryRepo.findAll();//  .stream().toList();
        log.info("Categories: {}", categories);
        List<CategoryDTO> categoryDTOs = Mapper.mapAll(categories, CategoryDTO.class);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS)
                .response(categoryDTOs)
                .build();

        return responseDTO;
    }

}
