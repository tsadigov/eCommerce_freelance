package com.project.ecommerce.service;

import com.project.ecommerce.dto.OrderDTO;
import com.project.ecommerce.dto.OrderUpdateDTO;
import com.project.ecommerce.dto.ResponseDTO;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface OrderService {

    ResponseDTO create(String username) throws NoSuchAlgorithmException;

    List<OrderDTO> getAllByUsername(String username);

    ResponseDTO update(OrderUpdateDTO orderUpdateDTO);

}
