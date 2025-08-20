package com.my.quiz.controller;


import com.my.quiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // 회원가입 페이지
    @GetMapping("/register")
    public String registerForm() {
        return "register";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           Model model) {
        try {
            userService.register(username, password);
            model.addAttribute("msg", "회원가입 완료");
        } catch (RuntimeException e) {
            model.addAttribute("msg", e.getMessage());
            return "register";
        }
        return "login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {
        User user = userService.login(username, password);
        if(user == null) {
            model.addAttribute("msg", "아이디 또는 비밀번호가 맞지 않거나 승인되지 않은 사용자입니다.");
            return "login";
        }

        session.setAttribute("loginUser", user);
        if(user.isAdmin()) {
            return "redirect:/admin";
        } else {
            return "redirect:/quiz";
        }
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    // 관리자 페이지: 승인되지 않은 사용자 목록
    @GetMapping("/admin")
    public String adminPage(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/login";
        }

        // 승인되지 않은 회원 목록 조회
        List<User> pendingUsers = userService.getPendingUsers();
        model.addAttribute("pendingUsers", pendingUsers);
        return "admin";
    }

    // 관리자: 사용자 승인
    @PostMapping("/admin/approve")
    public String approveUser(@RequestParam Long userId, HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/login";
        }
        userService.approveUser(userId);
        return "redirect:/admin";
    }

    // 관리자: 비밀번호 변경
    @PostMapping("/admin/changePassword")
    public String changePassword(@RequestParam Long userId,
                                 @RequestParam String newPassword,
                                 HttpSession session) {
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/login";
        }
        userService.changePassword(userId, newPassword);
        return "redirect:/admin";
    }
}
