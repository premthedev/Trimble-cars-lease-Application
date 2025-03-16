package com.trimble.carlease.controller;

import com.trimble.carlease.dto.UserRequest;
import com.trimble.carlease.model.User;
import com.trimble.carlease.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody UserRequest userRequest){
        if (userRequest == null) {
            logger.error("User details are missing");
            throw new IllegalArgumentException("User details are missing");
        }
        logger.info("Registering user with details: {}", userRequest);
        return userService.registerUser(userRequest);
    }

    @GetMapping("/get")
    public User getUserByEmail(@RequestParam String email){
        if (email == null) {
            logger.error("Email is missing or invalid in path variable ");
            throw new IllegalArgumentException("Email is missing");
        }
        logger.info("Fetching user with email: {}", email);
        return userService.getUserByEmail(email);
    }
}