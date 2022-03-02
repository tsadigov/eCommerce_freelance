package com.project.ecommerce.controller;

import com.project.ecommerce.dto.*;
import com.project.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @GetMapping("/{username}")
    ResponseEntity<ResponseDTO> getCustomer(@PathVariable String username){

        ResponseDTO responseDTO = userService.getCustomer(username);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PostMapping(path = "/register/customer")
    ResponseEntity<ResponseDTO> signUpCustomer(@RequestBody CustomerSignUpDTO customerSignUpDTO) {

        log.info("Request body -> {}", customerSignUpDTO);

        ResponseDTO responseDTO = userService.customerSignUp(customerSignUpDTO);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PostMapping(path = "/register/seller")
    ResponseEntity<ResponseDTO> signUpSeller(@RequestBody SellerSignUpDTO sellerSignUpDTO) {

        log.info("Request body -> {}", sellerSignUpDTO);

        ResponseDTO responseDTO = userService.sellerSignUp(sellerSignUpDTO);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PutMapping("/update/customer")
    ResponseEntity<ResponseDTO> updateCustomerProfile(@RequestBody CustomerDTO customerDTO){

        ResponseDTO responseDTO = userService.updateCustomerProfile(customerDTO);
        return ResponseEntity.ok()
                .body(responseDTO);
    }

}
