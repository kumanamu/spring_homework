package com.my.quiz.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminHome(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main"; // 일반 홈으로 돌려보냄
        }
        // 필요 시 추가 메시지/데이터
        model.addAttribute("adminMsg", "관리자페이지입니다");
        return "admin"; // 관리자 전용 대시보드
    }
}
