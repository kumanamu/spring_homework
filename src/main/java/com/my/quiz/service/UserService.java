package com.my.quiz.service;

import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.UserRepository;
import org.apache.catalina.Group;
import org.apache.catalina.Role;
import org.apache.catalina.User;
import org.apache.catalina.UserDatabase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Iterator;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    @Transactional
    public UserEntity register(String username, String password) {
        if(userRepository.existsByUsername(username)) {
            throw new RuntimeException("이미 존재하는 아이디입니다.");
        }

        User user = new User() {
            @Override
            public String getFullName() {
                return "";
            }

            @Override
            public void setFullName(String fullName) {

            }

            @Override
            public Iterator<Group> getGroups() {
                return null;
            }

            @Override
            public String getPassword() {
                return "";
            }

            @Override
            public void setPassword(String password) {

            }

            @Override
            public Iterator<Role> getRoles() {
                return null;
            }

            @Override
            public UserDatabase getUserDatabase() {
                return null;
            }

            @Override
            public String getUsername() {
                return "";
            }

            @Override
            public void setUsername(String username) {

            }

            @Override
            public void addGroup(Group group) {

            }

            @Override
            public void addRole(Role role) {

            }

            @Override
            public boolean isInGroup(Group group) {
                return false;
            }

            @Override
            public boolean isInRole(Role role) {
                return false;
            }

            @Override
            public void removeGroup(Group group) {

            }

            @Override
            public void removeGroups() {

            }

            @Override
            public void removeRole(Role role) {

            }

            @Override
            public void removeRoles() {

            }

            @Override
            public String getName() {
                return "";
            }
        };
        user.setUsername(username);
        user.setPassword(password);

        // 최초 root/admin 등록
        if("root".equals(username) && "admin".equals(password)) {
            Optional<User> adminOpt = userRepository.findByAdminTrue();
            if(adminOpt.isPresent()) {
                throw new RuntimeException("관리자는 이미 존재합니다.");
            }
            user.setAdmin(true);
            user.setStatus(true); // 관리자 자체 승인
        }

        return userRepository.save(user);
    }

    // 로그인
    public User login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if(userOpt.isEmpty()) return null;

        User user = userOpt.get();
        if(!user.getPassword().equals(password)) return null;

        // 일반회원 로그인 시 승인 여부 체크
        if(!user.isAdmin() && !user.isStatus()) return null;

        return user;
    }

    // 관리자: 사용자 승인
    @Transactional
    public User approveUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        user.setStatus(true);
        return userRepository.save(user);
    }

    // 관리자: 비밀번호 변경
    @Transactional
    public User changePassword(Long userId, String newPassword) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));
        user.setPassword(newPassword);
        return userRepository.save(user);
    }
}
