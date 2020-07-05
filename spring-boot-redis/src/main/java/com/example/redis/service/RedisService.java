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

    private RedisTemplate<String, Serializable> redisTemplate;

    @Autowired
    public RedisService(RedisTemplate<String, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addValue(Serializable obj) {
        redisTemplate.opsForValue().set("user", obj);
    }

}
