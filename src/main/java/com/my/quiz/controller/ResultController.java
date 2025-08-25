package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserAnswerEntity;
import com.my.quiz.service.ResultService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ResultController {

    private final ResultService resultService;

    @GetMapping("/results")
    public String results(@RequestParam(required = false) Long userId, Model model) {
        if (userId != null) {
            model.addAttribute("mode", "user");
            model.addAttribute("selectedUserId", userId);
            model.addAttribute("resultsByUser", resultService.findByUser(userId));
        } else {
            model.addAttribute("mode", "all");
            model.addAttribute("results", resultService.findAll());
        }
        return "results";
    }

}
