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
    public String results(@RequestParam(required = false) Long userId,
                          HttpSession session, Model model) {
        UserDto login = (UserDto) session.getAttribute("loginUser");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (login == null) return "redirect:/users/login";
        if (Boolean.FALSE.equals(login.getStatus())) return "redirect:/users/pending";

        List<UserAnswerEntity> list;

        if (Boolean.TRUE.equals(isAdmin)) {
            list = (userId == null) ? resultService.findAll() : resultService.findByUser(userId);
        } else {
            userId = login.getId();
            list = resultService.findByUser(userId);
        }

        model.addAttribute("list", list);
        model.addAttribute("isAdmin", Boolean.TRUE.equals(isAdmin));
        model.addAttribute("selectedUserId", userId);
        model.addAttribute("me", login);
        return "results";
    }
}
