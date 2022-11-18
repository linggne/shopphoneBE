package com.shopPhone.shopphoneBE.repository;

import org.springframework.data.repository.Repository;

import com.shopPhone.shopphoneBE.entity.Role;


public interface RoleRepository extends Repository<Role, Integer>{
    public Role findByName(String name);
}
