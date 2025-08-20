package com.my.quiz.controller;

import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quizzes")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /** 모든 문제 조회 (관리자 페이지 포함) */
    @GetMapping
    @ResponseBody
    public List<QuizEntity> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    /** 문제 추가 */
    @PostMapping
    @ResponseBody
    public QuizEntity addQuiz(@RequestParam String question, @RequestParam String answer) {
        QuizDto dto = new QuizDto();
        dto.setQuestion(question);
        dto.setAnswer(answer);
        return quizService.createQuiz(dto);
    }

    /** 랜덤 문제 가져오기 */
    @GetMapping("/random")
    @ResponseBody
    public QuizEntity getRandomQuiz() {
        return quizService.getRandomQuiz();
    }

    /** 관리자용 문제 관리 페이지 */
    @GetMapping("/manage")
    public String showQuizManagePage(Model model) {
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quizManage"; // templates/quizManage.html
    }
    @GetMapping("/quizzes/manage")
    public String showQuizManagePage(HttpSession session, Model model) {
        UserEntity user = (UserEntity) session.getAttribute("loginUser");
        if (user == null || !user.isAdmin()) {
            return "redirect:/"; // 관리자 아니면 메인으로 리다이렉트
        }
        model.addAttribute("quizzes", quizService.getAllQuizzes());
        return "quizManage";
    }
}
