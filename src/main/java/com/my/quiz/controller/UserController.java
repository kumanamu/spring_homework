package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.service.UserService;
import jakarta.servlet.http.HttpSession;
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
    @GetMapping("/signup")
    public String signupForm() {
        return "user/signup";
    }

    // 회원가입 처리
    @PostMapping("/signup")
    public String signup(@ModelAttribute UserDto userDto, Model model) {
        boolean success = userService.register(userDto);
        if (!success) {
            model.addAttribute("error", "관리자는 한 명만 등록 가능합니다.");
            return "user/signup";
        }
        return "redirect:/user/login";
    }

    // 로그인 페이지
    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    // 로그인 처리
    @PostMapping("/login")
    public String login(@ModelAttribute UserDto userDto,
                        HttpSession session,
                        Model model) {
        UserDto loginUser = userService.login(userDto);
        if (loginUser == null) {
            model.addAttribute("error", "로그인 실패. 승인 여부 또는 비밀번호를 확인하세요.");
            return "user/login";
        }
        session.setAttribute("loginUser", loginUser);
        return "redirect:/";
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 관리자만 회원 승인/관리
    @GetMapping("/manage")
    public String manageUsers(HttpSession session, Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/";
        }
        model.addAttribute("users", userService.findAll());
        return "user/manage";
    }

    @PostMapping("/approve/{id}")
    public String approveUser(@PathVariable Long id) {
        userService.approveUser(id);
        return "redirect:/user/manage";
    }

    @PostMapping("/updatePw/{id}")
    public String updatePassword(@PathVariable Long id, @RequestParam String newPassword) {
        userService.updatePassword(id, newPassword);
        return "redirect:/user/manage";
    }
}