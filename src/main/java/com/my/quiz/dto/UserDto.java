package com.my.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private boolean isAdmin;   // 관리자 여부
    private String status;     // 승인 상태 (e.g., PENDING, APPROVED, REJECTED)
    private int answerTrue;    // 맞춘 개수
    private int answerFalse;   // 틀린 개수
}
