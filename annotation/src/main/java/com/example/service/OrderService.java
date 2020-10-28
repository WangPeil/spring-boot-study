package com.example.service;

import com.example.annotation.OrderHandlerType;
import com.example.bean.Order;
import com.example.service.impl.OrderHandlerTypeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author wangpeil
 */
@Service
public class OrderService {
    private Map<OrderHandlerType, OrderHandler> orderHandlerMap;

    @Autowired
    public void setOrderHandlerMap(List<OrderHandler> orderHandlers) {
        orderHandlerMap = orderHandlers.stream().collect(
                Collectors.toMap(orderHandler -> AnnotationUtils.findAnnotation(orderHandler.getClass(), OrderHandlerType.class),
                        v -> v, (v1, v2) -> v1));
    }

    public void handleOrder(Order order) {
        OrderHandlerType orderHandlerType = new OrderHandlerTypeImpl(order.getSource());
        OrderHandler orderHandler = orderHandlerMap.get(orderHandlerType);
        orderHandler.handle(order);
    }
}
