package com.shopPhone.shopphoneBE.model;

import java.sql.Date;
import java.util.List;

import com.shopPhone.shopphoneBE.entity.Address;

public class OrderDetailResponse {
    private int id;
    private Date createdAt;
    private String  note;
    private Double totalPrice;
    private String typePayment;
    private String status;
    private Address address;
    private List<CartDetailResponse> cartDetailResponse;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public Date getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    public String getNote() {
        return note;
    }
    public void setNote(String note) {
        this.note = note;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getTypePayment() {
        return typePayment;
    }
    public void setTypePayment(String typePayment) {
        this.typePayment = typePayment;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public List<CartDetailResponse> getCartDetailResponse() {
        return cartDetailResponse;
    }
    public void setCartDetailResponse(List<CartDetailResponse> cartDetailResponse) {
        this.cartDetailResponse = cartDetailResponse;
    }
    public Address getAddress() {
        return address;
    }
    public void setAddress(Address address) {
        this.address = address;
    }
    
}
