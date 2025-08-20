package com.my.quiz.controller;

import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.service.QuizResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/results")
public class QuizResultController {

    @Autowired
    private QuizResultService quizResultService;

    // 결과 저장
    @PostMapping
    public QuizResultEntity saveResult(@RequestParam Long userId,
                                       @RequestParam Long quizId,
                                       @RequestParam boolean correct) {
        return quizResultService.saveResult(userId, quizId, correct);
    }

    // 특정 유저 결과 조회
    @GetMapping("/{userId}")
    public List<QuizResultEntity> getResultsByUser(@PathVariable Long userId) {
        return quizResultService.getResultsByUser(userId);
    }

    // 전체 결과 조회
    @GetMapping
    public List<QuizResultEntity> getAllResults() {
        return quizResultService.getAllResults();
    }
}
