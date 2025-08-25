package com.my.quiz.controller;

import com.my.quiz.dto.QuizForm;
import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.service.QuizService;
import com.my.quiz.service.ResultService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;
    private final ResultService resultService;

    /* ===== 항상 newQuiz 바인딩 보장 ===== */
    @ModelAttribute("newQuiz")
    public QuizForm newQuizAttr() {
        QuizForm form = new QuizForm();
        form.setAnswer(Boolean.TRUE); // 기본값: O
        return form;
    }

    /* ===== 관리자: 퀴즈 관리 ===== */

    @GetMapping("/quiz/manage")
    public String managePage(HttpSession session, Model model) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        List<QuizEntity> quizzes = quizService.findAll();
        model.addAttribute("quizzes", quizzes);
        return "quizManage";
    }

    @GetMapping("/quiz/new")
    public String newForm(HttpSession session, Model model,
                          @ModelAttribute("newQuiz") QuizForm form) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        // form 은 @ModelAttribute 로 이미 존재
        model.addAttribute("mode", "create");
        return "quizForm";
    }

    @PostMapping("/quiz/new")
    public String create(@ModelAttribute("newQuiz") QuizForm form,
                         HttpSession session, Model model) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        // (서버 검증이 있다면 실패 시 return "quizForm"; 하면 newQuiz 유지됨)
        boolean answer = Boolean.TRUE.equals(form.getAnswer());
        quizService.create(form.getQuestion(), answer);
        return "redirect:/quiz/manage";
    }

    @GetMapping("/quiz/{id}/edit")
    public String editForm(@PathVariable Long id,
                           HttpSession session,
                           Model model,
                           @ModelAttribute("newQuiz") QuizForm form) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        QuizEntity q = quizService.findById(id);
        // 기존 값으로 폼 채우기 (기본 객체에 덮어쓰기)
        form.setQuestion(q.getQuestion());
        form.setAnswer(q.isAnswer());

        model.addAttribute("mode", "edit");
        model.addAttribute("quizId", q.getId());
        return "quizForm";
    }

    @PostMapping("/quiz/{id}/edit")
    public String update(@PathVariable Long id,
                         @ModelAttribute("newQuiz") QuizForm form,
                         HttpSession session, Model model) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        boolean answer = Boolean.TRUE.equals(form.getAnswer());
        quizService.update(id, form.getQuestion(), answer);
        return "redirect:/quiz/manage";
    }

    @PostMapping("/quiz/{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpSession session, Model model) {
        if (!Boolean.TRUE.equals(session.getAttribute("isAdmin"))) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        quizService.delete(id);
        return "redirect:/quiz/manage";
    }

    /* ===== 사용자: 퀴즈 풀기 ===== */

    @GetMapping("/quiz")
    public String playPage(HttpSession session, Model model) {
        UserDto login = (UserDto) session.getAttribute("loginUser");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (login == null) return "redirect:/users/login";
        if (Boolean.FALSE.equals(isAdmin) && Boolean.FALSE.equals(login.getStatus())) {
            return "redirect:/users/pending";
        }
        List<QuizEntity> quizzes = quizService.findAll();
        model.addAttribute("quizzes", quizzes);
        return "quizPlay";
    }

    @PostMapping("/quiz/{id}/answer")
    public String submit(@PathVariable Long id,
                         @RequestParam("choice") String choiceStr,
                         HttpSession session) {
        UserDto login = (UserDto) session.getAttribute("loginUser");
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (login == null) return "redirect:/users/login";
        if (Boolean.FALSE.equals(isAdmin) && Boolean.FALSE.equals(login.getStatus())) {
            return "redirect:/users/pending";
        }
        boolean choice = "O".equalsIgnoreCase(choiceStr) || "true".equalsIgnoreCase(choiceStr);
        resultService.submitAnswer(login.getId(), id, choice);
        return "redirect:/quiz";
    }
}
