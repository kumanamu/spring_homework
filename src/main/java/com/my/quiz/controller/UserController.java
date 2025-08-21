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
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    // 회원가입 폼
    @GetMapping("/register")
    public String registerForm(Model model) {
        // 바인딩 객체가 필요하면 주입
        if (!model.containsAttribute("user")) {
            model.addAttribute("user", new UserDto());
        }
        return "userRegister";
    }

    // 회원가입 처리
    @PostMapping("/register")
    public String register(@ModelAttribute UserDto userDto, Model model) {
        try {
            userService.register(userDto);
            return "redirect:/users/login";
        } catch (Exception e) {
            model.addAttribute("error", "회원가입 오류: " + e.getMessage());
            return "userRegister";
        }
    }

    // 로그인 폼
    @GetMapping("/login")
    public String loginForm() {
        return "userLogin";
    }

    // 로그인 처리 (관리자 페이지 렌더링은 AdminController가 담당)
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        Model model) {

        // 고정 관리자 계정
        if ("admin".equals(username) && "1111".equals(password)) {
            UserDto admin = new UserDto();
            admin.setId(-1L);
            admin.setUsername("admin");
            session.setAttribute("loginUser", admin);
            session.setAttribute("isAdmin", true);
            return "redirect:/admin"; // 관리자 대시보드로만 리다이렉트
        }

        // 일반 사용자
        UserDto loginUser = userService.login(username, password);
        if (loginUser == null) {
            model.addAttribute("error", "아이디 또는 비밀번호가 올바르지 않습니다.");
            return "userLogin";
        }

        session.setAttribute("loginUser", loginUser);
        session.setAttribute("isAdmin", false);
        return "redirect:/"; // 일반 메인으로
    }

    // 로그아웃
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // (관리자 전용) 회원 목록
    @GetMapping("/list")
    public String listUsers(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        List<UserDto> users = userService.findAll(); // ← 이제 컴파일 OK
        model.addAttribute("users", users);
        return "userList";
    }
}
