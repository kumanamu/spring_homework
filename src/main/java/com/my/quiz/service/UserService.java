package com.my.quiz.service;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /** 회원가입 */
    public void register(UserDto dto) {
        UserEntity e = new UserEntity();
        e.setUsername(dto.getUsername());
        e.setPassword(dto.getPassword()); // 나중에 BCrypt 권장
        e.setCreatedAt(LocalDateTime.now());
        userRepository.save(e);
    }

    /** 로그인: 성공 시 DTO, 실패 시 null */
    public UserDto login(String username, String password) {
        Optional<UserEntity> opt = userRepository.findByUsernameAndPassword(username, password);
        return opt.map(this::toDto).orElse(null);
    }

    /** 관리자용 회원 목록: DTO 목록 반환 */
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    // ===== 내부 변환 유틸 =====
    private UserDto toDto(UserEntity e) {
        if (e == null) return null;
        UserDto dto = new UserDto();
        dto.setId(e.getId());
        dto.setUsername(e.getUsername());
        dto.setPassword(null); // 보안상 비번은 노출 X
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }
}
