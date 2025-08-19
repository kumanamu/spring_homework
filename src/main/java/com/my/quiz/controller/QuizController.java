package com.my.quiz.controller;

import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.service.QuizService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // 문제 리스트
    @GetMapping("/list")
    public String listQuizzes(Model model) {
        List<QuizEntity> quizzes = quizService.getAllQuizzes();
        model.addAttribute("quizzes", quizzes);
        return "quizList"; // quizList.html
    }

    // 문제 등록 페이지
    @GetMapping("/add")
    public String showAddForm() {
        return "quizAdd"; // quizAdd.html
    }

    // 문제 등록 처리
    @PostMapping("/add")
    public String addQuiz(@ModelAttribute QuizEntity quizEntity) {
        quizService.addQuiz(quizEntity);
        return "redirect:/quiz/list";
    }

    // 문제 삭제
    @PostMapping("/delete")
    public String deleteQuiz(@RequestParam Long id) {
        quizService.deleteQuiz(id);
        return "redirect:/quiz/list";
    }
}