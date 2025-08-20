package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("/")
    public String home(HttpSession session, Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        model.addAttribute("loginUser", loginUser);
        return "main";
    }
}