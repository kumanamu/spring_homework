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

    // 관리자 - 문제 등록 폼
    @GetMapping("/new")
    public String newQuizForm(HttpSession session) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/";
        }
        return "quiz/new";
    }

    // 문제 등록 처리
    @PostMapping("/new")
    public String newQuiz(@ModelAttribute QuizDto quizDto, HttpSession session) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/";
        }
        quizService.createQuiz(quizDto);
        return "redirect:/quiz/list";
    }

    // 문제 리스트 (관리자용)
    @GetMapping("/list")
    public String listQuizzes(HttpSession session, Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/";
        }
        model.addAttribute("quizzes", quizService.findAll());
        return "quiz/list";
    }

    // 문제 삭제
    @PostMapping("/delete/{id}")
    public String deleteQuiz(@PathVariable Long id, HttpSession session) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/";
        }
        quizService.deleteQuiz(id);
        return "redirect:/quiz/list";
    }

    // 일반 사용자 - 랜덤 문제 풀기
    @GetMapping("/solve")
    public String solveQuiz(HttpSession session, Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isStatus()) {
            return "redirect:/user/login";
        }
        QuizDto quiz = quizService.getRandomQuiz();
        model.addAttribute("quiz", quiz);
        return "quiz/solve";
    }

    // 문제 제출
    @PostMapping("/solve")
    public String solveQuiz(@RequestParam Long quizId,
                            @RequestParam String userAnswer,
                            HttpSession session,
                            Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isStatus()) {
            return "redirect:/user/login";
        }

        boolean correct = quizService.checkAnswer(loginUser, quizId, userAnswer);
        model.addAttribute("result", correct ? "정답!" : "오답!");
        return "quiz/result";
    }
}