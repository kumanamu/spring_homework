package com.my.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private Long id;
    private String question;
    private String answer;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
}
