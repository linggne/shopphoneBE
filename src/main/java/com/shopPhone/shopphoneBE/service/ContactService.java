package com.shopPhone.shopphoneBE.service;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.shopPhone.shopphoneBE.entity.Contact;
import com.shopPhone.shopphoneBE.repository.ContactRepository;

@Service
public class ContactService implements IContactService{

    private final ContactRepository contactRepository;

    public ContactService(ContactRepository contactRepository){
        this.contactRepository = contactRepository;
    }

    @Override
    public Boolean createContact(Contact contact) {
        try {
            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());
            contact.setCreatedAt(sqlDate);
            contactRepository.save(contact);
            return true;
        }catch(Exception err){
            err.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Contact> getAllContact() {
        return contactRepository.findAll();
    }
    
}
