package com.example.annotation;

import org.springframework.stereotype.Service;

import java.lang.annotation.*;

/**
 * @author wangpeil
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderHandlerType {
    String source();
}
