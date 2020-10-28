package com.example.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author wangpeil
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String source;
    private String code;
    private String payMethod;
}
