package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // 회원가입 폼
    @GetMapping("/register")
    public String showRegisterForm() {
        return "userRegister";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute UserDto dto, Model model) {
        UserEntity user = userService.register(dto);
        model.addAttribute("user", user);
        return "redirect:/users/login";
    }

    // 로그인 폼
    @GetMapping("/login")
    public String showLoginForm() {
        return "userLogin";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        var userOpt = userService.login(username, password);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            session.setAttribute("loginUser", user);  // ✅ 세션에 로그인 정보 저장
            return "main";  // main.html로 이동
        }
        model.addAttribute("error", "아이디 또는 비밀번호가 잘못되었습니다.");
        return "userLogin";

    }

    // 관리자용 회원 리스트
    @GetMapping("/list")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.findAll());
        return "userList";
    }

    // 회원 승인
    @PostMapping("/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        userService.approveUser(id);
        return "redirect:/users/list";
    }
}