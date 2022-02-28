package com.project.ecommerce.controller;

import com.project.ecommerce.dto.ResponseDTO;
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

    @PostMapping(path = "/register")
    ResponseEntity<ResponseDTO> signUp(@RequestBody SignUpDTO signUpDTO) {

        log.info("Request body -> {}", signUpDTO);

        userService.signUp(signUpDTO);

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(CREATED_CODE)
                .message(CREATED)
                .build();

        return ResponseEntity.ok()
                .body(responseDTO);
    }

}
