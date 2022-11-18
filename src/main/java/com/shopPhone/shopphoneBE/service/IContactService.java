package com.shopPhone.shopphoneBE.service;

import java.util.List;

import com.shopPhone.shopphoneBE.entity.Contact;

public interface IContactService {
    public Boolean createContact(Contact contact);
    public List<Contact> getAllContact();
}
