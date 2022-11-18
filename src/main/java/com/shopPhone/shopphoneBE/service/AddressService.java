package com.shopPhone.shopphoneBE.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopPhone.shopphoneBE.entity.Address;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.repository.AddressRepository;

@Service
public class AddressService implements IAddressService{

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository){
        this.addressRepository = addressRepository;
    }

    @Override
    public Boolean createAddress(Address address) {
        try {         
            List<Address> owlAddress = addressRepository.findAllByUserAndIsDeleted(address.getUser(), false);

            if(owlAddress.size() > 0) {
                Address owl = owlAddress.get(0);
                owl.setDeleted(true);
                addressRepository.save(owl);
            }
            addressRepository.save(address);

            return true;
        }catch(Exception err){
            err.printStackTrace();
            
            return false;
        }
    }

    @Override
    public Address getAddressByUser(int user_id) {
        try {
            User user = new User();
            user.setId(user_id);

            List<Address> addresses = addressRepository.findAllByUserAndIsDeleted(user, false);

            return addresses.get(0);
            
        }catch(Exception err) {
            err.printStackTrace();
            
            return null;
        }
    }
    
}
