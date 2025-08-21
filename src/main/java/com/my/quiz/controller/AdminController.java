package com.my.quiz.controller;

import com.my.quiz.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;

    private boolean notAdmin(HttpSession session) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        return (isAdmin == null || !isAdmin);
    }

    @GetMapping("/admin")
    public String adminHome(HttpSession session, Model model) {
        if (notAdmin(session)) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        model.addAttribute("adminMsg", "관리자페이지입니다");
        return "admin";
    }

    // ✅ 승인
    @PostMapping("/admin/users/{id}/approve")
    public String approve(@PathVariable Long id,
                          @RequestParam(required = false, defaultValue = "/users/list") String redirect,
                          HttpSession session, Model model) {
        if (notAdmin(session)) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        userService.setApproved(id, true);
        return "redirect:" + redirect;
    }

    // ✅ 거절(승인 회수)
    @PostMapping("/admin/users/{id}/reject")
    public String reject(@PathVariable Long id,
                         @RequestParam(required = false, defaultValue = "/users/list") String redirect,
                         HttpSession session, Model model) {
        if (notAdmin(session)) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        userService.setApproved(id, false);
        return "redirect:" + redirect;
    }
}
