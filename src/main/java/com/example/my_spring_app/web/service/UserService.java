package com.example.my_spring_app.web.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.my_spring_app.web.mapper.UserMapper;
import com.example.my_spring_app.web.model.User;

@Service
public class UserService {

    private final UserMapper userMapper;
    
    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }
    
    // すべてのユーザーを取得
    public List<User> getAllUsers() {
        return userMapper.findAll();
    }
    
    // IDでユーザーを取得
    public User getUserById(String id) {
        User user = userMapper.findById(id.toString());
        if (user == null) {
            throw new RuntimeException("User not found with id: " + id);
        }
        return user;
    }
    
    // 新しいユーザーを追加
    public User createUser(User user) {
        userMapper.insert(user);
        return user; // IDが自動設定される
    }
    
    // 既存のユーザーを更新
    public User updateUser(String id, User userDetails) {
        User user = getUserById(id);
        user.setName(userDetails.getName());
        user.setEmail(userDetails.getEmail());
        userMapper.update(user);
        return user;
    }
    
    // ユーザーを削除
    public void deleteUser(String id) {
        User user = getUserById(id);
        userMapper.delete(id.toString());
    }
}