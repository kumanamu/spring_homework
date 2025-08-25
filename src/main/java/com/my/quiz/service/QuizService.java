package com.my.quiz.service;

import com.my.quiz.entity.QuizEntity;
import com.my.quiz.repository.QuizRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final QuizRepository quizRepository;

    @Transactional(readOnly = true)
    public List<QuizEntity> findAll() {
        return quizRepository.findAll();
    }

    @Transactional(readOnly = true)
    public QuizEntity findById(Long id) {
        return quizRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("퀴즈를 찾을 수 없습니다. id=" + id));
    }

    @Transactional
    public QuizEntity create(String question, boolean answer) {
        QuizEntity q = QuizEntity.builder()
                .question(question)
                .answer(answer)
                .build();
        return quizRepository.save(q);
    }

    @Transactional
    public QuizEntity update(Long id, String question, boolean answer) {
        QuizEntity q = findById(id);
        q.setQuestion(question);
        q.setAnswer(answer);
        return q; // dirty checking
    }

    @Transactional
    public void delete(Long id) {
        quizRepository.deleteById(id);
    }
}
