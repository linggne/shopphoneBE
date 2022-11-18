package com.shopPhone.shopphoneBE.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopPhone.shopphoneBE.entity.Brand;
import com.shopPhone.shopphoneBE.service.IBrandService;

@CrossOrigin
@RestController
@RequestMapping("/api/brands")
public class BrandController {

    private final IBrandService brandService;

    public BrandController(IBrandService brandService){
        this.brandService = brandService;
    }
    
    @GetMapping("")
    public ResponseEntity<?> getAllBrand(){
        List<Brand> list = brandService.getAllBrand();
        return ResponseEntity.ok().body(list);
    }
}
