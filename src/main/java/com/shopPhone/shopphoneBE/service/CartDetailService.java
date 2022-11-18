package com.shopPhone.shopphoneBE.service;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopPhone.shopphoneBE.entity.Cart;
import com.shopPhone.shopphoneBE.entity.CartDetail;
import com.shopPhone.shopphoneBE.entity.Product;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.CartDetailRequest;
import com.shopPhone.shopphoneBE.repository.CartDetailRepository;
import com.shopPhone.shopphoneBE.repository.CartRepository;
import com.shopPhone.shopphoneBE.repository.ProductRepository;
import com.shopPhone.shopphoneBE.repository.UserRepository;

@Service
public class CartDetailService implements ICartDetailService{

    private final CartDetailRepository cartDetailRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Autowired
    public CartDetailService(CartDetailRepository cartDetailRepository, CartRepository cartRepository, UserRepository userRepository, ProductRepository productRepository){
        this.cartDetailRepository = cartDetailRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Boolean createCartDetail(CartDetailRequest cartDetailRequest) {
       try {
        User user = userRepository.findById(cartDetailRequest.getUserId()).get();
        Cart cart = cartRepository.findByUserAndIsDeleted(user, false);
        Product product = productRepository.findById(cartDetailRequest.getProductId()).get();

        if(product.getQuantity() < cartDetailRequest.getQuantity()) {
            return false;
        }
        product.setQuantity(product.getQuantity()  - cartDetailRequest.getQuantity());
        productRepository.save(product);
        CartDetail checkDetail = cartDetailRepository.findByCartAndProduct(cart, product);
        if(checkDetail != null){
            checkDetail.setQuantity(checkDetail.getQuantity() + cartDetailRequest.getQuantity());
            cartDetailRepository.save(checkDetail);
            cart.setCountProduct(cart.getCountProduct() + cartDetailRequest.getQuantity());
            double totalPrice = cart.getTotalPrice() + product.getPrice()*cartDetailRequest.getQuantity();
            cart.setTotalPrice(totalPrice);
            cartRepository.save(cart);
            return true;
        }
        CartDetail cartDetail = new CartDetail();
        
        if(cart == null){
            Cart cartNew  = new Cart();
            cartNew.setUser(user);
            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());
            cartNew.setCreatedAt(sqlDate);
            cartNew.setTotalPrice(0.0);
            cart = cartRepository.save(cartNew);
        }
        cartDetail.setCart(cart);
       
        cartDetail.setProduct(product);
        cartDetail.setQuantity(cartDetailRequest.getQuantity());
        cartDetailRepository.save(cartDetail);
        int count = cart.getCountProduct() + cartDetail.getQuantity();
        double totalPrice = cart.getTotalPrice() + product.getPrice()*cartDetailRequest.getQuantity();
        cart.setCountProduct(count);
        cart.setTotalPrice(totalPrice);
        cartRepository.save(cart);
        return true;
       }catch(Exception err){
        err.printStackTrace();
        return false;
       }
    }

    @Override
    public Boolean deleteCartDetail(int cartDetailId) {
        try {
            CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).get();
            Product product = productRepository.findById(cartDetail.getProduct().getId()).get();
            product.setQuantity(product.getQuantity() + cartDetail.getQuantity());
            productRepository.save(product);
            Cart cart = cartRepository.findById(cartDetail.getCart().getId()).get();
            cartDetailRepository.delete(cartDetail);
            cart.setCountProduct(cart.getCountProduct() - cartDetail.getQuantity());
            cart.setTotalPrice(cart.getTotalPrice() - cartDetail.getProduct().getPrice()*cartDetail.getQuantity());
            cartRepository.save(cart);
            return true;
        }catch (Exception err) {
            err.printStackTrace();
            return false;
        }
    }

    @Override
    public Boolean removeOneCountCartDetail(int cartDetailId) {
        try {
            CartDetail cartDetail = cartDetailRepository.findById(cartDetailId).get();
            cartDetail.setQuantity(cartDetail.getQuantity() - 1);
            cartDetailRepository.save(cartDetail);
            Product product = productRepository.findById(cartDetail.getProduct().getId()).get();
            product.setQuantity(product.getQuantity() + 1);
            productRepository.save(product);
            Cart cart = cartRepository.findById(cartDetail.getCart().getId()).get();
            cart.setCountProduct(cart.getCountProduct() - 1);
            cart.setTotalPrice(cart.getTotalPrice() - cartDetail.getProduct().getPrice());
            cartRepository.save(cart);
            return true;
        }catch (Exception err){
            err.printStackTrace();
            return false;
        }
    }
    
}
