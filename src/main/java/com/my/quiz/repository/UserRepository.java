package com.my.quiz.repository;

import com.my.quiz.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsernameAndPassword(String username, String password);

    Optional<UserEntity> findByUsername(String username);

    boolean existsByAdminTrue();

    // 승인 대기 목록
    List<UserEntity> findAllByStatusFalse();

    // 승인/거절(회수) - 단건 업데이트
    @Modifying
    @Query("update UserEntity u set u.status = :approved where u.id = :userId")
    int updateStatusById(Long userId, boolean approved);
}
