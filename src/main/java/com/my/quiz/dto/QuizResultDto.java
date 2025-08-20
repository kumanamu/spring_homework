package com.my.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizResultDto {
    private Long id;
    private Long userId;
    private int score;   // 점수
    private int correctCount;
    private int wrongCount;
}
