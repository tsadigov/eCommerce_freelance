package com.project.ecommerce.service;

import com.project.ecommerce.domain.AppUser;
import com.project.ecommerce.dto.BasketProductCreationDTO;
import com.project.ecommerce.dto.BasketProductDTO;
import com.project.ecommerce.dto.BasketProductUpdateDTO;
import com.project.ecommerce.dto.ResponseDTO;

import java.util.List;

public interface BasketService {

    ResponseDTO create(BasketProductCreationDTO basketProductCreationDTO);

    List<BasketProductDTO> getAllByUsername(String username);

    ResponseDTO getOneByProductIdAndUsername(Long id, String username);

    ResponseDTO updateAmount(BasketProductUpdateDTO basketProductUpdateDTO);

    ResponseDTO delete(Long id);

}
