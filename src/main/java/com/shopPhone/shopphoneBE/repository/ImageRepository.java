package com.shopPhone.shopphoneBE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Image;
@Repository
public interface ImageRepository extends JpaRepository<Image, Integer>{
    List<Image> findAllByProductId(int product_id);
    Boolean deleteAllByProductId(int productId);
}
