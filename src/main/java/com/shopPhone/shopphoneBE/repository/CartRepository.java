package com.shopPhone.shopphoneBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Cart;
import com.shopPhone.shopphoneBE.entity.User;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer>{
    public Cart findByUserAndIsDeleted(User user, Boolean isDeleted);
}
