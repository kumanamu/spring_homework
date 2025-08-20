package com.my.quiz.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizDto {
    private Long id;
    private String question;
    private String answer;   // 정답
    private String options;  // 보기 (쉼표구분 등)
}
