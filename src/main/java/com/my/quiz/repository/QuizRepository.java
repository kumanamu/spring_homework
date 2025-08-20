package com.my.quiz.repository;

import com.my.quiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuizRepository extends JpaRepository<QuizEntity, Long> {

    // 랜덤 문제 추출
    @Query(value = "SELECT * FROM quiz ORDER BY RAND() LIMIT ?1", nativeQuery = true)
    List<QuizEntity> findRandomQuizzes(int limit);
}
