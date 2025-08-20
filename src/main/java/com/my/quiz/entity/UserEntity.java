package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false, length = 100)
    private String password;

    // 관리자 여부 (true = 관리자, false = 일반회원)
    @Column(nullable = false)
    private boolean isAdmin;

    // 승인 상태 (true = 승인됨, false = 대기중)
    private String status; // status 필드


    // 정답 횟수
    @Column(nullable = false)
    private int answerTrue;

    // 오답 횟수
    @Column(nullable = false)
    private int answerFalse;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.answerTrue = 0;
        this.answerFalse = 0;
        this.status = String.valueOf(false); // 기본값: 미승인
        this.isAdmin = false; // 기본값: 일반회원
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    private String email; // 이메일 필드

}
