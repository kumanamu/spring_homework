package com.my.quiz.service;

import com.my.quiz.entity.QuizEntity;
import com.my.quiz.entity.UserAnswerEntity;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.QuizRepository;
import com.my.quiz.repository.UserAnswerRepository;
import com.my.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResultService {

    private final UserRepository userRepository;
    private final QuizRepository quizRepository;
    private final UserAnswerRepository userAnswerRepository;

    /** 제출 -> 채점 -> 기록 -> 맞/틀 누적 */
    @Transactional
    public UserAnswerEntity submitAnswer(Long userId, Long quizId, boolean choice) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));
        QuizEntity quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new IllegalArgumentException("퀴즈를 찾을 수 없습니다. id=" + quizId));

        boolean correct = (quiz.isAnswer() == choice);

        UserAnswerEntity rec = UserAnswerEntity.builder()
                .user(user)
                .quiz(quiz)
                .choice(choice)
                .correct(correct)
                .build();
        userAnswerRepository.save(rec);

        if (correct) user.setAnswerTrue(user.getAnswerTrue() + 1);
        else user.setAnswerFalse(user.getAnswerFalse() + 1);
        // dirty checking으로 UserEntity 업데이트

        return rec;
    }

    @Transactional(readOnly = true)
    public List<UserAnswerEntity> findByUser(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다. id=" + userId));
        return userAnswerRepository.findByUser(user);
    }

    @Transactional(readOnly = true)
    public List<UserAnswerEntity> findAll() {
        return userAnswerRepository.findAll();
    }
}
