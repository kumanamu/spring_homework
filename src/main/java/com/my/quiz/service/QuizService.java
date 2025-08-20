package com.my.quiz.service;

import com.my.quiz.entity.QuizEntity;
import com.my.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class QuizService {

    private final QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public List<QuizEntity> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public QuizEntity getQuizById(Long id) {
        return quizRepository.findById(id).orElse(null);
    }
}
