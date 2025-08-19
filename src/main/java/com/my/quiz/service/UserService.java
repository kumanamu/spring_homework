package com.my.quiz.service;

import com.my.quiz.repository.UserRepository;
import org.apache.catalina.User;

import java.util.List;
import java.util.Optional;

public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
    this.userRepository = userRepository;
    }

    public User joinUser(User user){
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public Optional<User> getUser(Long userId){
        return userRepository.findById(userId);
    }

    public List<User> getAllusers(){
        return userRepository.findAll();
    }
}
