package com.example.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author peili.wang
 */
@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public String exceptionHandler() {
        return null;
    }
}
