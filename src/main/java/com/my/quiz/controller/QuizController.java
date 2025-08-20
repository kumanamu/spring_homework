package com.my.quiz.controller;

import com.my.quiz.entity.QuizEntity;
import com.my.quiz.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/quizzes")
public class QuizController {

    @Autowired
    private QuizService quizService;

    // 모든 문제 조회
    @GetMapping
    public List<QuizEntity> getAll() {
        return quizService.getAll();
    }

    // 문제 추가
    @PostMapping
    public QuizEntity addQuiz(@RequestParam String question, @RequestParam String answer) {
        return quizService.addQuiz(question, answer);
    }

    // 랜덤 문제 n개 가져오기
    @GetMapping("/random")
    public List<QuizEntity> getRandom(@RequestParam(defaultValue = "5") int count) {
        return quizService.getRandom(count);
    }
}
