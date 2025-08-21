package com.my.quiz.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data // getter, setter, toString, equals, hashCode 자동 생성
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor
@Builder
// 모든 필드 생성자
public class UserDto {
    private String username;
    private String password;
    private String email;
    private boolean admin; // 관리자 여부
    private String nickname; // 새로 추가
    private Long id;
    private LocalDateTime createdAt;

}