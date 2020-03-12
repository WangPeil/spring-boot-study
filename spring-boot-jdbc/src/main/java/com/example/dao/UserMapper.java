package com.example.dao;

import com.example.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author peili.wang
 */
@Mapper
@Repository
public interface UserMapper {
    /**
     * 查找表中所有用户
     *
     * @return
     */
    List<User> getAllUser();

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    Integer addUser(User user);

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    Integer deleteUserById(@Param("id") Integer id);

    /**
     * 查找用户
     *
     * @param id
     * @return
     */
    User queryUserById(@Param("id") Integer id);

    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    Integer updateUser(User user);
}
