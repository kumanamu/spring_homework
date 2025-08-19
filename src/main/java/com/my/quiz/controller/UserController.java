package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지
    @GetMapping("/join")
    public String showRegisterForm() {
        return "userRegister"; // templates/userRegister.html
    }

    // 회원가입 처리
    @PostMapping("/join")
    public String registerUser(@ModelAttribute UserDto userDto) {
        // DTO → Entity 변환
        UserEntity userEntity = UserEntity.builder()
                .email(userDto.getEmail())
                .password(userDto.getPassword())
                .nickname(userDto.getNickname())
                .build();

        userService.joinUser(userEntity);
        return "redirect:/user/list";
    }

    // 회원 리스트
    @GetMapping("/list")
    public String listUsers(Model model) {
        List<UserEntity> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "userList"; // templates/userList.html
    }

    // 회원 삭제
    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/user/list";
    }
}
