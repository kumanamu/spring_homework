package com.my.quiz.controller;

import com.my.quiz.entity.UserEntity;
import com.my.quiz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입
    @PostMapping("/register")
    public UserEntity register(@RequestParam String username, @RequestParam String password) {
        return userService.register(username, password);
    }

    // 로그인
    @PostMapping("/login")
    public UserEntity login(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password).orElse(null);
    }
}
