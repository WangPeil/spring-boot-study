package com.example.springbootmybatis.pojo;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class User {
    private Integer id;
    private String username;
    private Integer age;
    private Timestamp createTime;
    private Timestamp updateTime;
}
