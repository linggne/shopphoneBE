package com.shopPhone.shopphoneBE.service;

import com.shopPhone.shopphoneBE.entity.Address;

public interface IAddressService {
    public Boolean createAddress(Address address);
    public Address getAddressByUser(int user_id);
}
