package com.my.quiz.entity;


import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "quiz_result")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 연관 없이 스냅샷으로 저장: 문제 id 및 문제 텍스트
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(nullable = false, length = 1000)
    private String quizQuestion;

    // 사용자가 입력한 답
    @Column(length = 200)
    private String userAnswer;

    // 정답 스냅샷
    @Column(length = 200)
    private String correctAnswer;

    // 점수(예: 정답 1, 오답 0)
    @Column(nullable = false)
    private int score;

    // 결과를 남긴 사용자 id
    @Column(name = "user_id")
    private Long userId;

    // 정오 플래그
    @Column(nullable = false)
    private boolean correct;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
