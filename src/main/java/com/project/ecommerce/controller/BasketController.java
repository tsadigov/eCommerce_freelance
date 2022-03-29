package com.project.ecommerce.controller;

import com.project.ecommerce.dto.BasketProductCreationDTO;
import com.project.ecommerce.dto.BasketProductDTO;
import com.project.ecommerce.dto.BasketProductUpdateDTO;
import com.project.ecommerce.dto.ResponseDTO;
import com.project.ecommerce.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/basket")
public class BasketController {

    private final BasketService basketService;

    @PostMapping
    public ResponseEntity<ResponseDTO> create(@RequestBody BasketProductCreationDTO basketProductCreationDTO){
        ResponseDTO responseDTO = basketService.create(basketProductCreationDTO);
        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @GetMapping("/{username}")
    public List<BasketProductDTO> getAll(@PathVariable String username){
        return basketService.getAllByUsername(username);
    }

    @PutMapping
    public ResponseEntity<ResponseDTO> updateAmount(@RequestBody BasketProductUpdateDTO basketProductUpdateDTO){
        ResponseDTO responseDTO = basketService.updateAmount(basketProductUpdateDTO);
        return ResponseEntity.ok()
                .body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> delete(@PathVariable Long id){
        ResponseDTO responseDTO = basketService.delete(id);
        return ResponseEntity.ok()
                .body(responseDTO);
    }

}
