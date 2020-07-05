package com.example.redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * pojo
 * @author wangpeil
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements Serializable {
    private Integer id;
    private Integer age;
    private String gender;

}
