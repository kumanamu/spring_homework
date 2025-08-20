package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 로그인용 아이디(유니크)
    @Column(nullable = false, unique = true, length = 100)
    private String username;

    // 단순 예제용으로 평문 저장(실전: 반드시 암호화)
    @Column(nullable = false, length = 200)
    private String password;

    // 관리자인지 여부 (root/admin 등록 시 true)
    @Column(nullable = false)
    private boolean admin = false;

    // 관리자의 승인 여부 (가입 직후 false, 관리자가 승인하면 true)
    @Column(nullable = false)
    private boolean status = false;

    // 정답/오답 누적
    @Column(nullable = false)
    private int answerTrue = 0;

    @Column(nullable = false)
    private int answerFalse = 0;

    // 생성/수정 타임스탬프 (자동 채움)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // 편의 메서드
    public void incrementAnswerTrue() {
        this.answerTrue = this.answerTrue + 1;
    }

    public void incrementAnswerFalse() {
        this.answerFalse = this.answerFalse + 1;
    }
}