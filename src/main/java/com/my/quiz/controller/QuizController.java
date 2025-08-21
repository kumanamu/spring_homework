package com.my.quiz.controller;

import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.service.QuizService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/quiz") // ✅ 템플릿에서 사용하는 경로와 일치시킴 (/quiz/manage 등)
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    /** (선택) 모든 문제 JSON 조회 - 서비스 시그니처(getAll)와 일치 */
    @GetMapping("/api")
    @ResponseBody
    public List<QuizEntity> getAllQuizzesApi() {
        return quizService.getAll();
    }

    /** (선택) 문제 추가(JSON) - 서비스 create(dto)와 일치하도록 조정 */
    @PostMapping("/api")
    @ResponseBody
    public ResponseEntity<?> addQuizApi(@RequestParam String question,
                                        @RequestParam String answer) {
        QuizDto dto = new QuizDto();
        dto.setQuestion(question);
        dto.setAnswer(answer);
        quizService.create(dto); // 서비스는 void 반환
        return ResponseEntity.ok().build();
    }

    /** (선택) 랜덤 문제 JSON 하나 */
    @GetMapping("/api/random")
    @ResponseBody
    public ResponseEntity<QuizEntity> getRandomQuizApi() {
        List<QuizEntity> list = quizService.getRandomQuizzes(1);
        QuizEntity one = list.isEmpty() ? null : list.get(0);
        return ResponseEntity.ok(one);
    }

    // ===================== 관리자 화면 영역 (Thymeleaf) =====================

    // 관리자: 문제 관리 화면
    @GetMapping("/manage")
    public String manage(HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        model.addAttribute("quizzes", quizService.getAll());
        model.addAttribute("newQuiz", new QuizDto());
        return "quizManage";
    }

    // 관리자: 문제 등록 (폼 전송)
    @PostMapping("/create")
    public String create(@ModelAttribute("newQuiz") QuizDto quizDto,
                         HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        quizService.create(quizDto);
        return "redirect:/quiz/manage"; // ✅ 클래스 매핑과 일치
    }

    // 관리자: 수정 폼 열기
    @GetMapping("/edit/{id}")
    public String editForm(@PathVariable Long id, HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        model.addAttribute("quiz", quizService.findById(id));
        model.addAttribute("quizzes", quizService.getAll()); // 리스트도 같이 보여주면 UX 좋음
        model.addAttribute("newQuiz", new QuizDto());
        return "quizManage";
    }

    // 관리자: 수정 처리
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable Long id,
                       @ModelAttribute QuizDto quizDto,
                       HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        quizService.update(id, quizDto);
        return "redirect:/quiz/manage";
    }

    // 관리자: 삭제
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Long id, HttpSession session, Model model) {
        Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");
        if (isAdmin == null || !isAdmin) {
            model.addAttribute("error", "관리자만 접근 가능합니다.");
            return "main";
        }
        quizService.delete(id);
        return "redirect:/quiz/manage";
    }
}
