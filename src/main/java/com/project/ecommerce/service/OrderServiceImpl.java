package com.project.ecommerce.service;

import com.project.ecommerce.dao.AppUserRepo;
import com.project.ecommerce.dao.BasketProductRepo;
import com.project.ecommerce.dao.OrderRepo;
import com.project.ecommerce.dao.ProductRepo;
import com.project.ecommerce.domain.OrderStatus;
import com.project.ecommerce.domain.Orders;
import com.project.ecommerce.dto.BasketProductDTO;
import com.project.ecommerce.dto.OrderDTO;
import com.project.ecommerce.dto.OrderUpdateDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.utils.HashGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.project.ecommerce.bootstrap.Constants.*;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class OrderServiceImpl implements OrderService{

    private final OrderRepo orderRepo;
    private final BasketProductRepo basketProductRepo;
    private final AppUserRepo userRepo;
    private final ProductRepo productRepo;

    @Override
    public ResponseDTO create(String username) throws NoSuchAlgorithmException {

        LocalDate now = LocalDate.now();
        String trackNumber = HashGenerator.generate(now.toString());

        List<Orders> orders = new ArrayList<>();

        List<BasketProductDTO> basketProductDTOList = basketProductRepo.getAllByUsername(username);

        for (BasketProductDTO basketProductDTO : basketProductDTOList) {
            Orders order = Orders.builder()
                    .amount(basketProductDTO.getBasketAmount())
                    .status(OrderStatus.PLACED)
                    .orderDate(now)
                    .trackNumber(trackNumber)
                    .product(productRepo.getById(basketProductDTO.getProductId()))
                    .user(userRepo.findByUsername(username))
                    .build();

            orders.add(order);
            basketProductRepo.deleteById(basketProductDTO.getId());
        }

        orderRepo.saveAll(orders);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(SUCCESS_CODE)
                .message(SUCCESS)
                .build();

        return responseDTO;
    }

    @Override
    public List<OrderDTO> getAllByUsername(String username) {
        List<OrderDTO> orders = orderRepo.getAllByUsername(username);
        return orders;
    }

    @Override
    public List<OrderDTO> getAllByStoreId(Long id) {
        List<OrderDTO> orders = orderRepo.getAllByStoreId(id);
        return orders;
    }

    @Override
    public ResponseDTO updateStatus(OrderUpdateDTO orderUpdateDTO) {
        Orders order = orderRepo.findById(orderUpdateDTO.getId()).get();
        order.setStatus(orderUpdateDTO.getStatus());
        orderRepo.save(order);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(UPDATED_CODE)
                .message(UPDATED)
                .build();

        return responseDTO;
    }
}
