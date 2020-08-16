package com.example.springbootmybatis.dao;


import com.example.springbootmybatis.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author wangpeil
 */
@Mapper
@Repository
public interface UserDao {
    /**
     * 查询用户
     * @param id
     * @return
     */
    @Select("select * from user where id = #{id}")
    User selectUser(@Param("id") String id);

}
