package com.my.quiz.controller;

import com.my.quiz.service.QuizResultService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/quiz/results")
public class QuizResultController {

    private final QuizResultService quizResultService;

    public QuizResultController(QuizResultService quizResultService) {
        this.quizResultService = quizResultService;
    }

    // 현재 로그인한 회원의 퀴즈 결과 보기
    @GetMapping
    public String viewResults(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null || !loginUser.isStatus()) {
            return "redirect:/login";
        }

        model.addAttribute("results", quizResultService.getResultsByUser(loginUser));
        return "quiz_results"; // quiz_results.html 템플릿 필요
    }
}
