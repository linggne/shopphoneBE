package com.shopPhone.shopphoneBE.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shopPhone.shopphoneBE.entity.Brand;
import com.shopPhone.shopphoneBE.repository.BrandRepository;

@Service
public class BrandService implements IBrandService{

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository){
        this.brandRepository = brandRepository;
    }
    @Override
    public List<Brand> getAllBrand() {
        return brandRepository.findAll();
    }
    
}
