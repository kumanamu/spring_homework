package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name="quiz")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuizEntity {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

@Column(nullable = false, length = 255)
    private String question;

@Column(nullable = false, length = 100)
    private String answer;

@Column(length = 500)
    private String options;
}
