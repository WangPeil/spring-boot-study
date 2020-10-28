package com.example.service.impl;

import com.example.annotation.OrderHandlerType;

import java.lang.annotation.Annotation;

/**
 * @author wangpeil
 */
public class OrderHandlerTypeImpl implements OrderHandlerType {
    private final String source;

    public OrderHandlerTypeImpl(String source) {
        this.source = source;
    }

    @Override
    public String source() {
        return source;
    }

    @Override
    public Class<? extends Annotation> annotationType() {
        return OrderHandlerType.class;
    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        hashCode += (127 * "source".hashCode()) ^ source.hashCode();
        return hashCode;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof OrderHandlerType)) {
            return false;
        }
        OrderHandlerType other = (OrderHandlerType) obj;
        return source.equals(other.source());
    }
}
