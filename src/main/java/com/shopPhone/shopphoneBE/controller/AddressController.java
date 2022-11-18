package com.shopPhone.shopphoneBE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopPhone.shopphoneBE.entity.Address;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.model.MessageResponse;
import com.shopPhone.shopphoneBE.service.IAddressService;
import com.shopPhone.shopphoneBE.service.IUserService;

@CrossOrigin
@RestController
@RequestMapping("/api/address")
public class AddressController {

    private final IAddressService addressService;

    @Autowired
    private IUserService userService;

    @Autowired
    public AddressController(IAddressService addressService, IUserService userService){
        this.addressService = addressService;
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<?> createdAddressNew(@RequestBody Address address) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        address.setUser(user);
        Boolean res = addressService.createAddress(address);
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("create success!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("create faild"));
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getAddress() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userService.findUserByUsername(username);
        Address address = addressService.getAddressByUser(user.getId());
        return ResponseEntity.ok(address);
    }
}
