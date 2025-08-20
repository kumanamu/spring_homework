package com.my.quiz.service;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserEntity register(UserDto dto) {
        // 관리자 등록 여부 확인
        boolean isAdmin = "root".equals(dto.getUsername()) && "admin".equals(dto.getPassword());
        if(isAdmin && userRepository.findAll().stream().anyMatch(UserEntity::isAdmin)) {
            throw new RuntimeException("관리자는 이미 등록되어 있습니다.");
        }

        UserEntity user = new UserEntity();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setAdmin(isAdmin);
        user.setStatus(isAdmin); // 관리자는 승인 필요 없음
        return userRepository.save(user);
    }

    public boolean login(String email, String password) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        return userOpt.filter(u -> u.getPassword().equals(password) && (u.isAdmin() || u.isStatus())).isPresent();
    }

    public void approveUser(Long userId) {
        userRepository.findById(userId).ifPresent(u -> {
            u.setStatus(true);
            userRepository.save(u);
        });
    }

    public void changePassword(Long userId, String newPassword) {
        userRepository.findById(userId).ifPresent(u -> {
            u.setPassword(newPassword);
            userRepository.save(u);
        });
    }
}