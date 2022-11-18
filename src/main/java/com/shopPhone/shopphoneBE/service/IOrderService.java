package com.shopPhone.shopphoneBE.service;

import java.util.List;

import com.shopPhone.shopphoneBE.entity.Order;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.OrderDetailResponse;

public interface IOrderService {
    public Boolean createOrder(Order order, int userId);
    public List<Order> getAllOrder();
    public Boolean updateStatusOrder(String status, int orderId);
    public List<Order> getOrderByUser(User user);
    public OrderDetailResponse getOrderById(int id);
}
