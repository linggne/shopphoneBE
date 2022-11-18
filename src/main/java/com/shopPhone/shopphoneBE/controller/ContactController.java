package com.shopPhone.shopphoneBE.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shopPhone.shopphoneBE.entity.Contact;
import com.shopPhone.shopphoneBE.model.MessageResponse;
import com.shopPhone.shopphoneBE.service.IContactService;

@CrossOrigin
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final IContactService contactService;

    @Autowired
    public ContactController(IContactService contactService) {
        this.contactService = contactService;
    }
    
    @PostMapping("")
    public ResponseEntity<?> createContact(@RequestBody Contact contact){
        Boolean res = contactService.createContact(contact);
        if(res) {
            return ResponseEntity.ok().body(new MessageResponse("created successful!"));
        }else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MessageResponse("create faild"));
        }
    }

    @GetMapping("")
    public ResponseEntity<?> getContact() {
        return ResponseEntity.ok().body(contactService.getAllContact());
    }
}
