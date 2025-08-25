package com.my.quiz.repository;

import com.my.quiz.entity.QuizEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<QuizEntity, Long> {
    // 기본 CRUD 기능은 JpaRepository가 제공


// 랜덤 문제 추출
@Query(value = "SELECT * FROM quizzes ORDER BY RAND() LIMIT ?1", nativeQuery = true)
List<QuizEntity> findRandomQuizzes(int limit);

}
