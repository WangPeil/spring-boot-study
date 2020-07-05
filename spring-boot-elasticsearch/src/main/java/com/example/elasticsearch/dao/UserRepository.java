package com.example.elasticsearch.dao;

import com.example.elasticsearch.pojo.User;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * @author wangpeil
 */
public interface UserRepository extends ElasticsearchRepository<User, Long> {
}
