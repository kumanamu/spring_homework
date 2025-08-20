package com.my.quiz.repository;

import com.my.quiz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);

    // 관리자가 한 명만 존재하도록 체크
    Optional<UserEntity> findByAdminTrue();
}
