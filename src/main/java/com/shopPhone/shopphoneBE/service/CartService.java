package com.shopPhone.shopphoneBE.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.shopPhone.shopphoneBE.entity.Cart;
import com.shopPhone.shopphoneBE.entity.CartDetail;
import com.shopPhone.shopphoneBE.entity.Image;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.CartDetailResponse;
import com.shopPhone.shopphoneBE.model.CartResponse;
import com.shopPhone.shopphoneBE.model.ImageModel;
import com.shopPhone.shopphoneBE.model.ProductResponse;
import com.shopPhone.shopphoneBE.repository.CartDetailRepository;
import com.shopPhone.shopphoneBE.repository.CartRepository;
import com.shopPhone.shopphoneBE.repository.ImageRepository;
import com.shopPhone.shopphoneBE.repository.UserRepository;

@Service
public class CartService implements ICartService{

    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final CartDetailRepository cartDetailRepository;
    private final ModelMapper modelMapper;
    private final ImageRepository imageRepository;
    
    @Autowired
    public CartService(CartRepository cartRepository, UserRepository userRepository, ModelMapper modelMapper, CartDetailRepository cartDetailRepository, ImageRepository imageRepository){
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.cartDetailRepository = cartDetailRepository;
        this.imageRepository = imageRepository;
    }

    @Override
    public Boolean createCart(int userId) {
        try {
            Cart cart = new Cart();
            User user = this.userRepository.findById(userId).get();
            cart.setUser(user);
            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());
            cart.setCreatedAt(sqlDate);
            cart.setCountProduct(0);
            cart.setTotalPrice(0.0);
            cartRepository.save(cart);
            return true;
        }catch(Exception err){
            err.printStackTrace();
            return false;
        }
    }

    @Override
    public CartResponse findCartByUser(int userId) {
        try {
            User user = userRepository.findById(userId).get();
            Cart cart = cartRepository.findByUserAndIsDeleted(user, false);
            CartResponse cartRes = modelMapper.map(cart, CartResponse.class);
            List<CartDetail> cartDetails = cartDetailRepository.findAllByCart(cart);
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
            cartRes.setCartDetails(listCartDetail);
            return cartRes;
        }catch(Exception err) {
            err.printStackTrace();
            return null;
        }
    }

    @Override
    public Boolean deleteCart(int cartId) {
        try {
            Cart cart = cartRepository.findById(cartId).get();
            cart.setDeleted(true);
            cartRepository.save(cart);
            return true;
        }catch (Exception err){
            err.printStackTrace();
            return false;
        }
    }
    
}
