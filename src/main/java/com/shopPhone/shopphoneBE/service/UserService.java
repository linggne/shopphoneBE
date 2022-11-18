package com.shopPhone.shopphoneBE.service;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.shopPhone.shopphoneBE.entity.Role;
import com.shopPhone.shopphoneBE.entity.User;
import com.shopPhone.shopphoneBE.repository.RoleRepository;
import com.shopPhone.shopphoneBE.repository.UserRepository;

@Service
public class UserService implements IUserService{


    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                        RoleRepository roleRepository,
                        BCryptPasswordEncoder bCryptPasswordEncoder){
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role role = roleRepository.findByName("Customer");
        user.setRole(role);
        return userRepository.save(user);
    }


    @Override
    public Boolean updateUser(User user) {
        try {
            User owl = userRepository.findById(user.getId()).get();
            owl.setEmail(user.getEmail());
            owl.setLastName(user.getLastName());
            owl.setFirstName(user.getFirstName());
            owl.setPhone(user.getPhone());
            java.util.Date date = new java.util.Date();
            Date sqlDate = new Date(date.getTime());
            owl.setUpdatedAt(sqlDate);
            userRepository.save(owl);

            return true;
        } catch(Exception err) {
            err.printStackTrace();
            return false;
        }
    }


    @Override
    public List<User> findAllUser() {
        Role role = new Role();
        role.setId(1);
        return userRepository.findAllByRole(role);
    }
    
}
