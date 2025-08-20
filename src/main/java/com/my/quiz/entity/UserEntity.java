package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickname; // NOT NULL

    @Column(nullable = false)
    private boolean admin;

    @Column(nullable = false)
    private String status;

    private int answerTrue;
    private int answerFalse;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
