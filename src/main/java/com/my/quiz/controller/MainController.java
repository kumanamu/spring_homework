package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping({"/", "/main"})
    public String home(HttpSession session, Model model) {
        Object obj = session.getAttribute("loginUser");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

        // ✅ 로그인된 일반 사용자이며 미승인 상태면 메인 접근도 우회
        if (obj instanceof UserDto user
                && (isAdmin == null || !isAdmin)
                && Boolean.FALSE.equals(user.getStatus() ){
            return "redirect:/users/pending";
        }
        return "main";
    }
}
