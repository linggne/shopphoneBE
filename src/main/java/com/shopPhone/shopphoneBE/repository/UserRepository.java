package com.shopPhone.shopphoneBE.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shopPhone.shopphoneBE.entity.Role;
import com.shopPhone.shopphoneBE.entity.User;


@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    public User findByEmail(String email);
    public User findByUsername(String username);
    public List<User> findAllByRole(Role role);
}
