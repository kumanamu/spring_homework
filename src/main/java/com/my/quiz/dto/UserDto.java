package com.my.quiz.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String password;   // 회원가입/로그인 시만 사용
    private boolean admin;     // 관리자 여부
    private boolean status;    // 승인 여부
    private int answerTrue;
    private int answerFalse;

}
