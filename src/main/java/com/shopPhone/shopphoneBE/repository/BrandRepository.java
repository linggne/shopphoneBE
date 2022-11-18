package com.shopPhone.shopphoneBE.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Brand;

@Repository
public interface BrandRepository extends JpaRepository<Brand, Integer>{
    
}
