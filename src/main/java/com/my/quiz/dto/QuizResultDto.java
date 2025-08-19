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
    private int score;
    private Long userId;   // UserEntity 대신 id만
    private Long quizId;   // QuizEntity 대신 id만
    private LocalDateTime createdAt;
}
