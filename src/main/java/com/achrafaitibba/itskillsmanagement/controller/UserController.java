package com.achrafaitibba.itskillsmanagement.controller;

import com.achrafaitibba.itskillsmanagement.dto.UserRegistrationRequest;
import com.achrafaitibba.itskillsmanagement.dto.UserRegistrationResponse;
import com.achrafaitibba.itskillsmanagement.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserRegistrationResponse registerUser(@RequestBody UserRegistrationRequest user){
        return userService.register(user);
    }


}
