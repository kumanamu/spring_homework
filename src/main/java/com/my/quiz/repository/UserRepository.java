package com.my.quiz.repository;

import com.my.quiz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // 로그인용 메서드
    Optional<UserEntity> findByUsernameAndPassword(String username, String password);
}
