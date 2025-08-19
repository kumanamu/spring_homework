package com.my.quiz.service;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.UserRepository;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
    }

    public UserEntity joinUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public UserEntity updateUser(UserEntity userEntity){
        return userRepository.save(userEntity);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public Optional<UserEntity> getUser(Long userId){
        return userRepository.findById(userId);
    }

    public List<UserEntity> getAllusers(){
        return userRepository.findAll();
    }

    public List<UserDto> getAllUsers() {
        return null;
    }
}
