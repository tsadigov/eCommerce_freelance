package com.project.ecommerce.service;

import com.project.ecommerce.dao.AppUserRepo;
import com.project.ecommerce.dao.BasketProductRepo;
import com.project.ecommerce.dao.ProductRepo;
import com.project.ecommerce.domain.BasketProduct;
import com.project.ecommerce.dto.BasketProductCreationDTO;
import com.project.ecommerce.dto.BasketProductDTO;
import com.project.ecommerce.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.project.ecommerce.bootstrap.Constants.CREATED;
import static com.project.ecommerce.bootstrap.Constants.CREATED_CODE;

@Service
@Slf4j
@RequiredArgsConstructor
public class BasketServiceImpl implements BasketService{

    private final BasketProductRepo basketProductRepo;
    private final AppUserRepo userRepo;
    private final ProductRepo productRepo;

    @Override
    public ResponseDTO create(BasketProductCreationDTO basketProductCreationDTO) {

        System.out.println(basketProductCreationDTO.getProductId()+ " "+ basketProductCreationDTO.getAmount()+ " "+ basketProductCreationDTO.getUsername());


        BasketProduct basketProduct = BasketProduct.builder()
                .amount(basketProductCreationDTO.getAmount())
                .user(userRepo.findByUsername(basketProductCreationDTO.getUsername()))
                .product(productRepo.getById(basketProductCreationDTO.getProductId()))
                .build();

        basketProductRepo.save(basketProduct);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(CREATED_CODE)
                .message(CREATED)
                .response(basketProduct)
                .build();

        return responseDTO;
    }

    @Override
    public List<BasketProductDTO> getAll() {

        List<BasketProductDTO> basketProducts = basketProductRepo.getAllByUsername_named();
        return basketProducts;
    }


}
