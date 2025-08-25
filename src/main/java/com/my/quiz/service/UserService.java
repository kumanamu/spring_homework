package com.my.quiz.service;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /** 회원가입: 가입 후 status=false(미승인)로 저장 */
    @Transactional
    public void register(UserDto dto) {
        // 아이디 중복 방지
        userRepository.findByUsername(dto.getUsername()).ifPresent(u -> {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        });

        // 기본값
        boolean isAdmin = false;
        boolean isApproved = false; // 미승인

        UserEntity e = UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())   // 운영 시 BCrypt 권장
                .email(dto.getEmail())
                .nickname(dto.getNickname())
                .admin(isAdmin)
                .status(isApproved)            // ✅ 가입 직후 미승인
                .answerTrue(0)
                .answerFalse(0)
                .build();

        userRepository.save(e);
    }

    /** 로그인: 성공 시 DTO, 실패 시 null */
    @Transactional(readOnly = true)
    public UserDto login(String username, String password) {
        Optional<UserEntity> opt = userRepository.findByUsernameAndPassword(username, password);
        return opt.map(this::toDtoWithoutPassword).orElse(null);
    }

    /** 전체 회원 목록 */
    @Transactional(readOnly = true)
    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::toDtoWithoutPassword)
                .collect(Collectors.toList());
    }

    /** 승인 대기 목록 */
    @Transactional(readOnly = true)
    public List<UserDto> findPending() {
        return userRepository.findAllByStatusFalse().stream()
                .map(this::toDtoWithoutPassword)
                .collect(Collectors.toList());
    }

    /** 승인/거절(회수) 처리 */
    @Transactional
    public void setApproved(Long userId, boolean approved) {
        int updated = userRepository.updateStatusById(userId, approved);
        if (updated == 0) {
            throw new IllegalArgumentException("대상 사용자를 찾을 수 없습니다: id=" + userId);
        }
    }

    // ===== 내부 변환 =====
    private UserDto toDtoWithoutPassword(UserEntity e) {
        UserDto dto = new UserDto();
        dto.setId(e.getId());
        dto.setUsername(e.getUsername());
        dto.setPassword(null); // 비밀번호는 노출 금지
        dto.setEmail(e.getEmail());
        dto.setNickname(e.getNickname());
        dto.setAdmin(e.isAdmin());
        dto.setStatus(e.isStatus());
        dto.setAnswerTrue(e.getAnswerTrue());
        dto.setAnswerFalse(e.getAnswerFalse());
        dto.setCreatedAt(e.getCreatedAt());
        return dto;
    }
}
