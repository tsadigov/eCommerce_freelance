package com.project.ecommerce.controller;

import com.project.ecommerce.dao.StoreRepo;
import com.project.ecommerce.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final StoreRepo storeRepo;

    @GetMapping
    public List<Store> Test(){
        return storeRepo.findAll();
    }

}
