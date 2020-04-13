package com.example.musicdemo.repository;

import com.example.musicdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUserName(String userName);
    void deleteByUserName(String userName);
}
