package com.my.quiz.repository;


import com.my.quiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
    // 필요 시 추가 쿼리 작성 가능
}
