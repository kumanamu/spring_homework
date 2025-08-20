package com.my.quiz.service;

import com.my.quiz.entity.QuizEntity;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.repository.QuizRepository;
import com.my.quiz.repository.QuizResultRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class QuizResultService {
    private final QuizResultRepository resultRepository;

    public QuizResultEntity saveResult(Long userId, int correct, int incorrect) {
        QuizResultEntity result = new QuizResultEntity();
        result.setUserId(userId);
        result.setAnswerTrue(correct);
        result.setAnswerFalse(incorrect);
        return resultRepository.save(result);
    }

    public List<QuizResultEntity> getResultsByUser(Long userId) {
        return resultRepository.findByUserId(userId);
    }
}
