package com.my.quiz.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String email;
    private String password;  // 로그인/회원가입시만 사용
    private String nickname;


}
