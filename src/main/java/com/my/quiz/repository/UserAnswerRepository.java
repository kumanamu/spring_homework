package com.my.quiz.repository;

import com.my.quiz.entity.UserAnswerEntity;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAnswerRepository extends JpaRepository<UserAnswerEntity, Long> {
    List<UserAnswerEntity> findByUser(UserEntity user);
    List<UserAnswerEntity> findByQuiz(QuizEntity quiz);
}
