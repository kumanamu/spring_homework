package com.my.quiz.service;

import com.my.quiz.dto.UserDto;
import com.my.quiz.entity.UserEntity;
import com.my.quiz.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<UserEntity> getAllUsers(){
        return userRepository.findAll();
    }


    public UserEntity register(UserEntity user){
        return userRepository.save(user);
    }


    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
}
