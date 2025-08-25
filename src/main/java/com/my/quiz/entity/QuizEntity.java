package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "quizzes")
public class QuizEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** 문제 내용 */
    @Column(nullable = false, length = 500)
    private String question;

    /** 정답: true=O, false=X */
    @Column(nullable = false)
    private boolean answer;

    @CreationTimestamp
    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable=false)
    private LocalDateTime updatedAt;
}
