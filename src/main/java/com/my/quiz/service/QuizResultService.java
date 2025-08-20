package com.my.quiz.service;


import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.repository.QuizResultRepository;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class QuizResultService {

    private final QuizResultRepository quizResultRepository;

    public QuizResultService(QuizResultRepository quizResultRepository) {
        this.quizResultRepository = quizResultRepository;
    }

    @Transactional
    public QuizResultEntity saveResult(User user, int correct, int wrong) {
        QuizResultEntity result = new QuizResultEntity();
        result.setUser(user);
        result.setAnswerTrue(correct);
        result.setAnswerFalse(wrong);
        return quizResultRepository.save(result);
    }

    public List<QuizResultEntity> getResultsByUser(User user) {
        return quizResultRepository.findByUser(user);
    }
}
