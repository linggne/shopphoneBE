package com.shopPhone.shopphoneBE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Brand;
import com.shopPhone.shopphoneBE.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{
    public List<Product> findByIsDeleted(boolean isDeleted);
    public List<Product> findAllByBrandAndIsDeleted(Brand brand, boolean isDeleted);
    public List<Product> findAllByStatusAndIsDeleted(String status, boolean isDeleted);
    public List<Product> findAllByTypeAndIsDeleted(String type, boolean isDeleted);  
}
