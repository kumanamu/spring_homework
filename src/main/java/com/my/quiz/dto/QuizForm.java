package com.my.quiz.dto;

import lombok.Data;

@Data
public class QuizForm {
    private String question; // 문항
    private Boolean answer;  // 정답: O=true, X=false
}
