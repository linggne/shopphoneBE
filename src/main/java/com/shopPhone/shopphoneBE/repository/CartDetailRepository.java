package com.shopPhone.shopphoneBE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Cart;
import com.shopPhone.shopphoneBE.entity.CartDetail;
import com.shopPhone.shopphoneBE.entity.Product;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer>{
    public List<CartDetail> findAllByCart(Cart cart);
    public  CartDetail findByCartAndProduct(Cart cart, Product product);
}
