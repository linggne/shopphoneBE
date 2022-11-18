package com.shopPhone.shopphoneBE.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer>{
    
}
