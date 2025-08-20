package com.my.quiz.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizResultDto {
    private Long id;
    private Long quizId;
    private String quizQuestion;
    private String userAnswer;
    private String correctAnswer;
    private int score;
    private Long userId;
    private boolean correct;
    private LocalDateTime createdAt;
}
