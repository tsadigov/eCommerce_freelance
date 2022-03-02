package com.project.ecommerce.controller;

import com.project.ecommerce.dto.CustomerSignUpDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.dto.SellerSignUpDTO;
import com.project.ecommerce.dto.SignUpDTO;
import com.project.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.project.ecommerce.bootstrap.Constants.CREATED;
import static com.project.ecommerce.bootstrap.Constants.CREATED_CODE;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping(path = "/register/customer")
    ResponseEntity<ResponseDTO> signUp(@RequestBody CustomerSignUpDTO customerSignUpDTO) {

        log.info("Request body -> {}", customerSignUpDTO);

        ResponseDTO responseDTO = userService.customerSignUp(customerSignUpDTO);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @PostMapping(path = "/register/seller")
    ResponseEntity<ResponseDTO> signUp(@RequestBody SellerSignUpDTO sellerSignUpDTO) {

        log.info("Request body -> {}", sellerSignUpDTO);

        ResponseDTO responseDTO = userService.sellerSignUp(sellerSignUpDTO);

        return ResponseEntity.ok()
                .body(responseDTO);
    }

}
