package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

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

    // 문제 텍스트
    @Column(nullable = false, length = 1000)
    private String question;

    // 정답 텍스트 (ex: "O" 또는 "42" 등)
    @Column(nullable = false, length = 200)
    private String answer;

    // 보기(선택형) 등 필요하면 쉼표구분으로 저장 (옵션, 없어도 됨)
    @Column(length = 1000)
    private String options;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}