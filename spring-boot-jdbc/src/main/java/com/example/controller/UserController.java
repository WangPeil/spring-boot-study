package com.example.controller;

import com.example.pojo.User;
import com.example.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author peili.wang
 */
@RestController
public class UserController {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getAllUser")
    public String getAllUser() throws JsonProcessingException {
        LOGGER.info("into getAllUser method");
        return OBJECT_MAPPER.writeValueAsString(userService.getAllUser());
    }

    @PostMapping("/addUser/{name}/{age}")
    public String addUser(@PathVariable String name, @PathVariable Integer age) throws JsonProcessingException {
        LOGGER.info("into addUser method");
        return OBJECT_MAPPER.writeValueAsString(userService.addUser(new User(null, name, age)));
    }

    @DeleteMapping("/deleteUser/{id}")
    public String deleteUserById(@PathVariable Integer id) throws JsonProcessingException {
        LOGGER.info("into deleteUserById method");
        return OBJECT_MAPPER.writeValueAsString(userService.deleteUserById(id));
    }

    @GetMapping("/queryUserById/{id}")
    public String queryUserById(@PathVariable Integer id) throws JsonProcessingException {
        LOGGER.info("into queryUserById method");
        return OBJECT_MAPPER.writeValueAsString(userService.queryUserById(id));
    }

    @PutMapping("/updateUser/{id}/{name}/{age}")
    public String updateUser(@PathVariable Integer id, @PathVariable String name,
                             @PathVariable Integer age) throws JsonProcessingException {
        LOGGER.info("into updateUser method");
        return OBJECT_MAPPER.writeValueAsString(userService.updateUser(new User(id, name, age)));
    }
}
