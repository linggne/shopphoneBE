package com.shopPhone.shopphoneBE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.CartDetailRequest;
import com.shopPhone.shopphoneBE.model.CartResponse;
import com.shopPhone.shopphoneBE.model.MessageResponse;
import com.shopPhone.shopphoneBE.service.ICartDetailService;
import com.shopPhone.shopphoneBE.service.ICartService;
import com.shopPhone.shopphoneBE.service.IUserService;

@CrossOrigin
@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    private final ICartService cartService;
    private final ICartDetailService cartDetailService;
    private final IUserService userService;

    @Autowired
    public CartController(ICartDetailService cartDetailService, ICartService cartService, IUserService userService){
        this.cartDetailService = cartDetailService;
        this.cartService = cartService;
        this.userService = userService;
    }

    @GetMapping("/user")
    public ResponseEntity<?> getCart(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        CartResponse cart = cartService.findCartByUser(user.getId());
        return ResponseEntity.ok().body(cart);
    }

    @PostMapping("")
    public ResponseEntity<?> createCart(){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        Boolean res = cartService.createCart(user.getId());
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("created cart successful!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("create cart faild"));
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<?> deleteCart(@PathVariable int cartId){
        Boolean res = cartService.deleteCart(cartId);
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("deleted cart successful!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("delete cart faild"));
        }
    }

    @PostMapping("/detail")
    public ResponseEntity<?> createCartDetail(@RequestBody CartDetailRequest cartDetailRequest){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        cartDetailRequest.setUserId(user.getId());
        Boolean res = cartDetailService.createCartDetail(cartDetailRequest);
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("created cart detail successful!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("create cart detail faild"));
        }
    }

    @DeleteMapping("/detail/{cartDetailId}")
    public ResponseEntity<?> deleteCartDetail(@PathVariable int cartDetailId){
        Boolean res = cartDetailService.deleteCartDetail(cartDetailId);
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("deleted cart detail successful!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("delete cart detail faild"));
        }
    }

    @PutMapping("/detail/{cartDetailId}")
    public ResponseEntity<?> removeOneCountCartDetail(@PathVariable int cartDetailId){
        Boolean res = cartDetailService.removeOneCountCartDetail(cartDetailId);
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("remove count cart detail successful!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("remove count cart detail faild"));
        }
    }
}
