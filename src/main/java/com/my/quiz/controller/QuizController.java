package com.my.quiz.controller;

import com.my.quiz.service.QuizService;
import com.my.quiz.service.QuizResultService;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;
    private final QuizResultService quizResultService;

    public QuizController(QuizService quizService, QuizResultService quizResultService) {
        this.quizService = quizService;
        this.quizResultService = quizResultService;
    }

    // 퀴즈 페이지
    @GetMapping
    public String quizPage(HttpSession session, Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null || !loginUser.isStatus()) {
            return "redirect:/login";
        }

        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quiz";
    }

    // 퀴즈 제출 처리
    @PostMapping("/submit")
    public String submitQuiz(HttpSession session,
                             @RequestParam int correct,
                             @RequestParam int wrong,
                             Model model) {
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null || !loginUser.isStatus()) {
            return "redirect:/login";
        }

        quizResultService.saveResult(loginUser, correct, wrong);
        model.addAttribute("msg", "퀴즈 결과가 저장되었습니다.");
        return "redirect:/quiz/results";
    }
}
