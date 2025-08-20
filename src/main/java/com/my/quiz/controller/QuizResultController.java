package com.my.quiz.controller;

import com.my.quiz.dto.QuizResultDto;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.service.QuizResultService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/result")
public class QuizResultController {

    private final QuizResultService quizResultService;

    // 내 결과 보기
    @GetMapping("/my")
    public String myResults(HttpSession session, Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isStatus()) {
            return "redirect:/user/login";
        }
        model.addAttribute("results", quizResultService.findByUserId(loginUser.getId()));
        return "result/my";
    }

    // 관리자용 전체 결과 보기
    @GetMapping("/all")
    public String allResults(HttpSession session, Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null || !loginUser.isAdmin()) {
            return "redirect:/";
        }
        model.addAttribute("results", quizResultService.findAll());
        return "result/all";
    }
}
