package com.shopPhone.shopphoneBE.service;

import java.util.List;

import com.shopPhone.shopphoneBE.entity.User;

public interface IUserService {
    public User findUserByEmail(String email);
    public User findUserByUsername(String username);
    public User saveUser(User user);
    public Boolean updateUser(User user);
    public List<User> findAllUser();
}