package com.my.quiz.controller;

import com.my.quiz.dto.QuizResultDto;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.service.QuizResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/result")
public class QuizResultController {

    private final QuizResultService resultService;

    public QuizResultController(QuizResultService resultService) {
        this.resultService = resultService;
    }

    // 결과 리스트 (전체)
    @GetMapping("/list")
    public String listResults(Model model) {
        List<QuizResultEntity> results = resultService.getAllResults();
        model.addAttribute("results", results);
        return "resultList"; // resultList.html
    }

    // 특정 회원 결과 조회
    @GetMapping("/user/{userId}")
    public String userResults(@PathVariable Long userId, Model model) {
        List<QuizResultEntity> results = resultService.getResultsByUser(userId);
        model.addAttribute("results", results);
        return "userResultList"; // userResultList.html
    }

    // 결과 저장
    @PostMapping("/save")
    public String saveResult(@ModelAttribute QuizResultEntity result) {
        resultService.saveResult(result);
        return "redirect:/result/list";
    }
}
