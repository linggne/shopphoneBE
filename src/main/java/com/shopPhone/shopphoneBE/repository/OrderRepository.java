package com.shopPhone.shopphoneBE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer>{
    
}
