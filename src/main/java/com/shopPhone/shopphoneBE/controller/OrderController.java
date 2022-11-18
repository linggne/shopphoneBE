package com.shopPhone.shopphoneBE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopPhone.shopphoneBE.entity.Order;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.MessageResponse;
import com.shopPhone.shopphoneBE.service.IOrderService;
import com.shopPhone.shopphoneBE.service.IUserService;

@CrossOrigin
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    
    private final IOrderService orderService;
    private final IUserService userService;

    @Autowired
    public OrderController(IOrderService orderService, IUserService userService) {
        this.orderService = orderService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<?> createOrder(@RequestBody Order order){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        Boolean res = orderService.createOrder(order, user.getId());
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("create success!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("create faild"));
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAllOrder() {
        return ResponseEntity.ok().body(orderService.getAllOrder());
    }

    @PutMapping("")
    public ResponseEntity<?> updateStatusOrder(@RequestBody Order order) {
        Boolean res = orderService.updateStatusOrder(order.getStatus(), order.getId());
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("create success!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("create faild"));
        }
    }

    @GetMapping("/user")
    public ResponseEntity<?> getOrderByUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        return ResponseEntity.ok().body(orderService.getOrderByUser(user));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> getOrderById (@PathVariable int id){
        return ResponseEntity.ok().body(orderService.getOrderById(id));
    }


}
