package com.project.ecommerce.service;

import com.project.ecommerce.dao.AppUserRepo;
import com.project.ecommerce.dao.BasketProductRepo;
import com.project.ecommerce.dao.ProductRepo;
import com.project.ecommerce.domain.BasketProduct;
import com.project.ecommerce.domain.Product;
import com.project.ecommerce.dto.*;
import com.project.ecommerce.utils.Mapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

import static com.project.ecommerce.bootstrap.Constants.*;

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
    public List<BasketProductDTO> getAllByUsername(String username) {

        List<BasketProductDTO> basketProducts = basketProductRepo.getAllByUsername(username);
        return basketProducts;
    }

    @Override
    public ResponseDTO getOneByProductId(Long id) {

        Optional<BasketProduct> basketProduct = basketProductRepo.findBasketProductByProductId(id);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS)
                .response(basketProduct)
                .build();

        return responseDTO;
    }

    @Override
    public ResponseDTO updateAmount(BasketProductUpdateDTO basketProductUpdateDTO) {

        BasketProduct basketProduct = basketProductRepo.findById(basketProductUpdateDTO.getId()).get();
        basketProduct.setAmount(basketProductUpdateDTO.getAmount());
        basketProductRepo.save(basketProduct);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(UPDATED_CODE)
                .message(UPDATED)
                .response(basketProduct)
                .build();

        return responseDTO;
    }

    @Override
    public ResponseDTO delete(Long id) {
        BasketProduct basketProduct = basketProductRepo.findById(id).get();
        basketProductRepo.delete(basketProduct);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(DELETED_CODE)
                .message(DELETED)
                .build();

        return responseDTO;
    }


}
