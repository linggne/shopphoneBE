package com.shopPhone.shopphoneBE.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shopPhone.shopphoneBE.entity.Address;
import com.shopPhone.shopphoneBE.entity.Cart;
import com.shopPhone.shopphoneBE.entity.CartDetail;
import com.shopPhone.shopphoneBE.entity.Image;
import com.shopPhone.shopphoneBE.entity.Order;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.CartDetailResponse;
import com.shopPhone.shopphoneBE.model.ImageModel;
import com.shopPhone.shopphoneBE.model.OrderDetailResponse;
import com.shopPhone.shopphoneBE.model.ProductResponse;
import com.shopPhone.shopphoneBE.repository.AddressRepository;
import com.shopPhone.shopphoneBE.repository.CartDetailRepository;
import com.shopPhone.shopphoneBE.repository.CartRepository;
import com.shopPhone.shopphoneBE.repository.ImageRepository;
import com.shopPhone.shopphoneBE.repository.OrderRepository;

@Service
public class OrderService implements IOrderService{

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final AddressRepository addressRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderService(OrderRepository orderRepository, 
                        CartRepository cartRepository, 
                        AddressRepository addressRepository, 
                        CartDetailRepository cartDetailRepository, 
                        ImageRepository imageRepository,
                        ModelMapper modelMapper){
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.addressRepository = addressRepository;
        this.cartDetailRepository = cartDetailRepository;
        this.imageRepository = imageRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Boolean createOrder(Order order, int userId) {
        try {
            User user = new User();
            user.setId(userId);
            Cart cart = cartRepository.findByUserAndIsDeleted(user, false);
            order.setCart(cart);

            Address address = addressRepository.findAllByUserAndIsDeleted(user, false).get(0);
            order.setAddress(address);
            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());
            order.setCreatedAt(sqlDate);
            order.setStatus("Mới");
            orderRepository.save(order);
            cart.setDeleted(true);
            cartRepository.save(cart);
            return true;

        }catch(Exception err){
            err.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    @Override
    public Boolean updateStatusOrder(String status, int orderId) {
        try {
            Order order = orderRepository.findById(orderId).get();
            order.setStatus(status);
            orderRepository.save(order);
            return true;
        }catch (Exception err) {
            err.printStackTrace();

            return false;
        }
    }

    @Override
    public List<Order> getOrderByUser(User user) {
        List<Order> orders =  orderRepository.findAll();
        List<Order> orderUser = new ArrayList<>();
        for(Order order: orders) {
            if(order.getCart().getUser().getId() == user.getId()){
                orderUser.add(order);
            }
        }
        return orderUser;
    }

    @Override
    public OrderDetailResponse getOrderById(int id) {
        Order order  = orderRepository.findById(id).get();
        List<CartDetail> cartDetails = cartDetailRepository.findAllByCart(order.getCart());
        List<CartDetailResponse> listCartDetail = new ArrayList<>();
        for(CartDetail item: cartDetails){
            CartDetailResponse res = new CartDetailResponse();
            res.setId(item.getId());
            res.setQuantity(item.getQuantity());
            List<Image> images = imageRepository.findAllByProductId(item.getProduct().getId());
            List<ImageModel> imageRes = new ArrayList<>();
            for(Image image: images){
                String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/images/")
                .path(image.getId() + "").toUriString();
                ImageModel imageModel = modelMapper.map(image, ImageModel.class);
                imageModel.setUrl(url);
                imageRes.add(imageModel);
            }
            ProductResponse product = modelMapper.map(item.getProduct(), ProductResponse.class);
            product.setImage(imageRes);
            res.setProduct(product);
            listCartDetail.add(res);
        }
        OrderDetailResponse res = new OrderDetailResponse();
        res.setId(order.getId());
        res.setAddress(order.getAddress());
        res.setCartDetailResponse(listCartDetail);
        res.setCreatedAt(order.getCreatedAt());
        res.setNote(order.getNote());
        res.setStatus(order.getStatus());
        res.setTotalPrice(order.getTotalPrice());
        res.setTypePayment(order.getTypePayment());
        return res;
    }
    
}
