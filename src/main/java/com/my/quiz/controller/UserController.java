package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.service.UserService;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private UserEntity user;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지
    @GetMapping("/join")
    public String showRegisterForm() {
        return "userRegister"; // userRegister.html
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String registerUser(@ModelAttribute UserDto userDto) {
        userService.joinUser(user);
        return "redirect:/user/list";
    }

    // 회원 리스트
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<UserDto> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList"; // userList.html
    }

    // 회원 삭제
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/user/list";
    }
}