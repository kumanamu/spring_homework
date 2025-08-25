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

    // 널 안전 & getXxx() 사용 위해 래퍼 타입
    private Boolean admin;
    private Boolean status;

    private Integer answerTrue;
    private Integer answerFalse;
    private LocalDateTime createdAt;
}
