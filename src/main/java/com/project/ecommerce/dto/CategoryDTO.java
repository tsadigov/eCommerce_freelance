package com.project.ecommerce.dto;

import lombok.Data;

import java.util.Set;

@Data
public class CategoryDTO {

    private String categoryName;
    private Set<String> subcategories;

}
