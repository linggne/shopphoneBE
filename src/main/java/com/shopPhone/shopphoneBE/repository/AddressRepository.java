package com.shopPhone.shopphoneBE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Address;
import com.shopPhone.shopphoneBE.entity.User;

@Repository
public interface AddressRepository  extends JpaRepository<Address, Integer>{
    List<Address> findAllByUserAndIsDeleted(User user, Boolean isDeleted);
}
