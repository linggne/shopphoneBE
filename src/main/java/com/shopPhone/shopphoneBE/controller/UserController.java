package com.shopPhone.shopphoneBE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.MessageResponse;
import com.shopPhone.shopphoneBE.service.IUserService;

@CrossOrigin
@RestController
@RequestMapping("/api/users")
public class UserController {
    
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PutMapping("")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userAuth = userService.findUserByUsername(username);
        user.setId(userAuth.getId());
        Boolean res = userService.updateUser(user);
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("create success!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("create faild"));
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User userAuth = userService.findUserByUsername(username);
        userAuth.setPassword("");
        return ResponseEntity.ok().body(userAuth);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAllUser() {
        return ResponseEntity.ok().body(userService.findAllUser());
    }
}
