package com.my.quiz.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserDto {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String nickname;

    private Boolean admin;    // 세션/권한 체크용
    private Boolean status;   // 승인 여부

    private Integer answerTrue;
    private Integer answerFalse;
    private LocalDateTime createdAt;
}
