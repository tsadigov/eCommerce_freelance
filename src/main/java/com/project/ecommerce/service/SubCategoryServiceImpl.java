package com.project.ecommerce.service;

import com.project.ecommerce.dao.CategoryRepo;
import com.project.ecommerce.dao.SubcategoryRepo;
import com.project.ecommerce.domain.Category;
import com.project.ecommerce.domain.Subcategory;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SubCategoryDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.project.ecommerce.bootstrap.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {

    private final CategoryRepo categoryRepo;
    private final SubcategoryRepo subcategoryRepo;

    @Override
    public ResponseDTO getOne(Long id) {
        Subcategory subcategory = subcategoryRepo.getById(id);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS)
                .response(subcategory)
                .build();

        return responseDTO;
    }

    @Override
    public ResponseDTO getAll() {
        List<Subcategory> subcategories = subcategoryRepo.findAll();
        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS)
                .response(subcategories)
                .build();

        return responseDTO;
    }

    @Transactional
    @Override
    public ResponseDTO create(SubCategoryDTO subCategoryDTO) {

        ResponseDTO responseDTO;
        Category category = categoryRepo.getCategoryByCategoryName(subCategoryDTO.getCategoryName());

        if (category == null) {
            category = Category.builder()
                    .categoryName(subCategoryDTO.getCategoryName())
                    .build();
            categoryRepo.save(category);
        }

        Subcategory subCategory = subcategoryRepo.findSubcategoryBySubCategoryName(subCategoryDTO.getSubCategoryName());
        if(subCategory == null){
            subCategory = Subcategory.builder()
                    .subCategoryName(subCategoryDTO.getSubCategoryName())
                    .category(category)
                    .build();
            subcategoryRepo.save(subCategory);

            responseDTO = ResponseDTO.builder()
                    .code(CREATED_CODE)
                    .message(CREATED)
                    .build();
        }
        else{
            responseDTO = ResponseDTO.builder()
                    .code(INTERNAL_SERVER_ERROR_CODE)
                    .message(CANNOT_BE_CREATED)
                    .build();
        }

        return responseDTO;
    }
}
