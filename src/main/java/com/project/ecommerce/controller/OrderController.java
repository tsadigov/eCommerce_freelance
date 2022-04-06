package com.project.ecommerce.controller;

import com.project.ecommerce.dto.OrderCreationDTO;
import com.project.ecommerce.dto.OrderDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody OrderCreationDTO orderCreationDTO) throws NoSuchAlgorithmException {
        ResponseDTO responseDTO = orderService.create(orderCreationDTO.getUsername());
        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @GetMapping("/{username}")
    public List<OrderDTO> getAllByUsername(@PathVariable String username){
        List<OrderDTO> orders = orderService.getAllByUsername(username);
        return orders;
    }

}
