package com.my.quiz.controller;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.service.QuizResultService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/results")
public class QuizResultController {

    private final QuizResultService quizResultService;

    /**
     * (로그인 사용자) 내 결과 목록
     * GET /results/my
     * - 로그인 필요
     * - 결과는 quizResult.html에서 렌더링
     */
    @GetMapping("/my")
    public String myResults(HttpSession session, Model model) {
        UserDto loginUser = (UserDto) session.getAttribute("loginUser");
        if (loginUser == null) {
            model.addAttribute("error", "로그인이 필요합니다.");
            return "userLogin";
        }

        List<QuizResultEntity> list = quizResultService.getResultsByUser(loginUser.getId());
        model.addAttribute("results", list);
        return "quizResult";
    }

    /**
     * (관리자) 전체 결과 목록
     * GET /results/all
     * - 관리자 권한 필요
     */
    @GetMapping("/all")
    public String allResults(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }

        List<QuizResultEntity> list = quizResultService.getAllResults();
        model.addAttribute("results", list);
        return "quizResult";
    }

    /**
     * (관리자) 특정 회원 결과 조회
     * GET /results/by-user?userId=123
     * - 관리자 권한 필요
     */
    @GetMapping("/by-user")
    public String byUser(@RequestParam Long userId, HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }

        List<QuizResultEntity> list = quizResultService.getResultsByUser(userId);
        model.addAttribute("results", list);
        return "quizResult";
    }
}
