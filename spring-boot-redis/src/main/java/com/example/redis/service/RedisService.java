package com.example.redis.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.Serializable;

/**
 * @author wangpeil
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String, Serializable> redisTemplate;

    public void addValue(Serializable obj) {
        redisTemplate.opsForValue().set("user",obj);
    }

}
