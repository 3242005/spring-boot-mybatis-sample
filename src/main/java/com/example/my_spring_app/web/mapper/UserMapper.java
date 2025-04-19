package com.example.my_spring_app.web.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.my_spring_app.web.model.User;

@Mapper
public interface UserMapper {
    
    List<User> findAll();
    
    User findById(@Param("param") String param);
    
    int insert(User user);
    
    int update(User user);
    
    int delete(@Param("param") String param);
}