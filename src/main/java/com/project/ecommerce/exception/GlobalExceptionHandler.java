package com.project.ecommerce.exception;

import com.project.ecommerce.dto.ResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import static com.project.ecommerce.bootstrap.Constants.*;

@RestControllerAdvice(basePackages = {})
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseDTO> notFoundExceptionHandling(ResourceNotFoundException exception) {

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(NOT_FOUND_CODE)
                .message(exception.getLocalizedMessage())
                .response(null).build();

        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseDTO> badCredentials(BadCredentialsException exception) {

        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(UNAUTHORIZED_CODE)
                .message(exception.getMessage())
                .response(null).build();

        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }

    @ExceptionHandler(AlreadyExistException.class)
    public ResponseEntity<ResponseDTO> alreadyExist(AlreadyExistException exception){
        ResponseDTO responseDTO = ResponseDTO.builder()
                .code(FORBIDDEN_CODE)
                .message(exception.getLocalizedMessage())
                .response(null)
                .build();

        return new ResponseEntity(responseDTO, HttpStatus.OK);
    }
}
