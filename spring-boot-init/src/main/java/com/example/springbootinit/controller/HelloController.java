package com.example.springbootinit.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peili.wang
 */
@RestController
public class HelloController {
    @GetMapping("/hello")
    public String hello() {
        return "hello, spring boot";
    }
}
