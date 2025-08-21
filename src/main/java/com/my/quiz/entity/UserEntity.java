package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "users", indexes = {
        @Index(name = "uk_users_username", columnList = "username", unique = true)
})
public class UserEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, length=50, unique=true)
    private String username;

    @Column(nullable=false, length=100)
    private String password;

    @Column(nullable=false, length=100)
    private String email;

    @Column(nullable=false, length=50)
    private String nickname;

    @Column(nullable=false)
    private boolean admin;

    @Column(nullable=false)
    private boolean status;

    @Column(nullable=false)
    private int answerTrue;

    @Column(nullable=false)
    private int answerFalse;

    @CreationTimestamp
    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable=false)
    private LocalDateTime updatedAt;
}
