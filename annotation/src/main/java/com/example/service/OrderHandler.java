package com.example.service;

import com.example.bean.Order;

/**
 * @author wangpeil
 */
public interface OrderHandler {
    /**
     * 处理订单
     * @param order 订单
     */
    void handle(Order order);
}
