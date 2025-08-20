package com.my.quiz.service;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // 회원가입
    public UserEntity register(UserDto dto) {
        // nickname이 없으면 username으로 기본값 설정
        String nickname = dto.getNickname() != null && !dto.getNickname().isEmpty()
                ? dto.getNickname()
                : dto.getUsername();

        UserEntity user = UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .email(dto.getEmail())
                .nickname(nickname)
                .admin(dto.isAdmin())
                .status("PENDING") // 기본 상태
                .answerTrue(0)
                .answerFalse(0)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return userRepository.save(user);
    }

    // 로그인
    public Optional<UserEntity> login(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password)
                .stream().findFirst();
    }

    // 모든 회원 조회 (관리자용)
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    // 회원 승인 (관리자용)
    public boolean approveUser(Long userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            user.setStatus("APPROVED");
            user.setUpdatedAt(LocalDateTime.now());
            userRepository.save(user);
            return true;
        }
        return false;
    }
    // 회원 정보 수정 (관리자용)
    public UserEntity updateUser(UserEntity updatedUser) {
        Optional<UserEntity> userOpt = userRepository.findById(updatedUser.getId());
        if (userOpt.isPresent()) {
            UserEntity user = userOpt.get();
            user.setUsername(updatedUser.getUsername());
            user.setEmail(updatedUser.getEmail());
            user.setNickname(updatedUser.getNickname());
            user.setAdmin(updatedUser.isAdmin());
            user.setStatus(updatedUser.getStatus());
            user.setUpdatedAt(LocalDateTime.now());
            return userRepository.save(user);
        }
        return null;
    }

    // 회원 삭제 (관리자용)
    public boolean deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }
    // 관리자용 전체 회원 조회
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }
}
