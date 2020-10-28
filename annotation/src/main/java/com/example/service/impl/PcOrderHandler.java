package com.example.service.impl;

import com.example.annotation.OrderHandlerType;
import com.example.bean.Order;
import com.example.service.OrderHandler;
import org.springframework.stereotype.Service;

/**
 * @author wangpeil
 */
@Service
@OrderHandlerType(source = "pc")
public class PcOrderHandler implements OrderHandler {
    @Override
    public void handle(Order order) {
        System.out.println("pc处理订单");
    }
}
