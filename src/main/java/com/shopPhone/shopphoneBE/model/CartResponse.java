package com.shopPhone.shopphoneBE.model;

import java.util.List;

public class CartResponse {

    private int id;
    private int countProduct;
    private Double totalPrice;
    private int userId;
    private List<CartDetailResponse> cartDetails;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCountProduct() {
        return countProduct;
    }
    public void setCountProduct(int countProduct) {
        this.countProduct = countProduct;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public List<CartDetailResponse> getCartDetails() {
        return cartDetails;
    }
    public void setCartDetails(List<CartDetailResponse> cartDetails) {
        this.cartDetails = cartDetails;
    }
}
