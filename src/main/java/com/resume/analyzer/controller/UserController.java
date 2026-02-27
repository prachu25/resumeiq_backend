package com.resume.analyzer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.resume.analyzer.dto.LoginResponse;
import com.resume.analyzer.model.User;
import com.resume.analyzer.service.UserService;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userServ;

    // This handles HTTP POST request
    @PostMapping("/register")
    public User UserRegister(@RequestBody User user) {
        // @RequestBody converts incoming JSON into User object.

        return userServ.registerUser(user);
    }

    // LOGIN
    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody User user) {

        User existingUser = userServ.loginUser(
                user.getEmail(),
                user.getPassword());

        if (existingUser != null) {

            return new LoginResponse(
                    "Login Successful...", // msg
                    existingUser.getId(), // userId
                    existingUser.getEmail()); // email
        }

        // If login fails
        return new LoginResponse("Invalid Credentials", null, null);
    }

}
