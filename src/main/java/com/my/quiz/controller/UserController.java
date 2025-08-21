package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import com.my.quiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /* ===================== 홈은 MainController에서 담당 ===================== */

    /* ===================== 회원가입 ===================== */
    @GetMapping("/users/register")
    public String registerForm(Model model) {
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDto());
        }
        return "userRegister";
    }

    @PostMapping("/users/register")
    public String register(@ModelAttribute UserDto userDto, Model model) {
        try {
            userService.register(userDto);
            // ✅ 가입 직후엔 승인 대기 화면으로 보냄
            return "redirect:/users/pending";
        } catch (Exception e) {
            model.addAttribute("error", "회원가입 오류: " + e.getMessage());
            return "userRegister";
        }
    }

    /* ===================== 로그인/로그아웃 ===================== */
    @GetMapping("/users/login")
    public String loginForm() { return "userLogin"; }

    @PostMapping("/users/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // 고정 관리자 계정
        if ("admin".equals(username) && "1111".equals(password)) {
            UserDto admin = new UserDto();
            admin.setId(-1L);
            admin.setUsername("admin");
            admin.setAdmin(true);
            admin.setStatus(true);
            session.setAttribute("loginUser", admin);
            session.setAttribute("isAdmin", true);
            return "redirect:/admin";
        }

        // 일반 사용자 인증
        UserDto loginUser = userService.login(username, password);
        if (loginUser == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "userLogin";
        }

        // ✅ 미승인 사용자는 세션 부여 없이 대기 화면
        if (Boolean.FALSE.equals(loginUser.getAdmin()) && Boolean.FALSE.equals(loginUser.getStatus())) {
            return "redirect:/users/pending";
        }

        // 승인된 사용자만 세션
        session.setAttribute("loginUser", loginUser);
        session.setAttribute("isAdmin", Boolean.TRUE.equals(loginUser.getAdmin()));
        return "redirect:/";
    }

    @GetMapping("/users/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /* ===================== 승인 대기 안내 ===================== */
    @GetMapping("/users/pending")
    public String pending() { return "pending"; }

    /* ===================== (관리자 전용) 회원 목록 ===================== */
    @GetMapping("/users/list")
    public String listUsers(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        List<UserDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "userList";
    }
}
