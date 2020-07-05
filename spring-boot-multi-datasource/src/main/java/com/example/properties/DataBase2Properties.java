package com.example.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: peili.wang
 * @Date: 2020/3/22 21:58
 * @Description:
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource.datasource2")
@Data
public class DataBase2Properties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    private String type;
}
