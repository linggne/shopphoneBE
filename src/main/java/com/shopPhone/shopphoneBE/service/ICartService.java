package com.shopPhone.shopphoneBE.service;

import java.util.Map;

import org.hibernate.sql.Delete;

import com.shopPhone.shopphoneBE.model.CartResponse;

public interface ICartService {
    public Boolean createCart(int userId);
    public CartResponse findCartByUser(int userId);
    public Boolean deleteCart(int cartId);
}
