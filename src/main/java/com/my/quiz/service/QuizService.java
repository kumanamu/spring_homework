package com.my.quiz.service;

import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor // final 필드에 대해 생성자 자동 생성
public class QuizService {

    private final QuizRepository quizRepository;

    /** 모든 퀴즈 조회 */
    public List<QuizEntity> getAll() {
        return quizRepository.findAll();
    }

    /** 컨트롤러에서 실수로 부르는 경우가 있어 별칭 제공 (선택) */
    public List<QuizEntity> getAllQuizzes() {
        return getAll();
    }

    /** 퀴즈 단건 조회 */
    public QuizEntity findById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }

    /** 랜덤 N문제 추출 (리포지토리 의존 없이 내부 셔플) */
    public List<QuizEntity> getRandomQuizzes(int limit) {
        List<QuizEntity> all = quizRepository.findAll();
        if (all.isEmpty() || limit <= 0) return Collections.emptyList();

        // 복사 후 셔플 → 앞에서 limit 만큼 추출
        List<QuizEntity> copy = new ArrayList<>(all);
        Collections.shuffle(copy);
        if (limit >= copy.size()) return copy;
        return copy.subList(0, limit);
    }

    /** 퀴즈 생성 */
    @Transactional
    public void create(QuizDto dto) {
        // QuizEntity에 @Builder가 있어야 합니다.
        QuizEntity quiz = QuizEntity.builder()
                .question(dto.getQuestion())
                .answer(dto.getAnswer())
                .build();
        quizRepository.save(quiz);
    }

    /** 퀴즈 수정 */
    @Transactional
    public void update(Long id, QuizDto dto) {
        Optional<QuizEntity> optionalQuiz = quizRepository.findById(id);
        if (optionalQuiz.isPresent()) {
            QuizEntity quiz = optionalQuiz.get();
            quiz.setQuestion(dto.getQuestion());
            quiz.setAnswer(dto.getAnswer());
            quizRepository.save(quiz);
        } else {
            // 필요시 예외를 던지거나 로그 처리
            // throw new IllegalArgumentException("Quiz not found: " + id);
        }
    }

    /** 퀴즈 삭제 */
    @Transactional
    public void delete(Long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
        } else {
            // 필요시 무시/예외/로그 처리
        }
    }
}
