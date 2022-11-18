package com.shopPhone.shopphoneBE.model;

public class CartDetailResponse {

    private int id;
    private ProductResponse product;
    private int quantity;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public ProductResponse getProduct() {
        return product;
    }
    public void setProduct(ProductResponse product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
    
}
