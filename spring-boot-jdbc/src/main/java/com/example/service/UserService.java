package com.example.service;

import com.example.dao.UserMapper;
import com.example.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author peili.wang
 */
@Service
public class UserService {
    UserMapper mapper;

    @Autowired
    public UserService(UserMapper mapper) {
        this.mapper = mapper;
    }

    public List<User> getAllUser() {
        return mapper.getAllUser();
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer addUser(User user) {
        return mapper.addUser(user);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer deleteUserById(Integer id) {
        return mapper.deleteUserById(id);
    }

    public User queryUserById(Integer id) {
        return mapper.queryUserById(id);
    }

    @Transactional(rollbackFor = Exception.class)
    public Integer updateUser(User user) {
        return mapper.updateUser(user);
    }
}
