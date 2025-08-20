package com.my.quiz.service;

import com.my.quiz.entity.QuizEntity;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.QuizRepository;
import com.my.quiz.repository.QuizResultRepository;
import com.my.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuizResultService {

    @Autowired
    private QuizResultRepository quizResultRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuizRepository quizRepository;

    // 결과 저장
    public QuizResultEntity saveResult(Long userId, Long quizId, boolean correct) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        Optional<QuizEntity> quizOpt = quizRepository.findById(quizId);

        if (userOpt.isPresent() && quizOpt.isPresent()) {
            UserEntity user = userOpt.get();
            QuizEntity quiz = quizOpt.get();

            if (correct) {
                user.setAnswerTrue(user.getAnswerTrue() + 1);
            } else {
                user.setAnswerFalse(user.getAnswerFalse() + 1);
            }
            userRepository.save(user);

            QuizResultEntity result = new QuizResultEntity();
            result.setUserId(userId);
            result.setQuizId(quizId);
            result.setCorrect(correct);
            result.setCreatedAt(LocalDateTime.now());
            return quizResultRepository.save(result);
        }
        return null;
    }

    // 특정 유저 결과 조회
    public List<QuizResultEntity> getResultsByUser(Long userId) {
        return quizResultRepository.findByUserId(userId);
    }

    // 전체 결과 조회
    public List<QuizResultEntity> getAllResults() {
        return quizResultRepository.findAll();
    }
}
