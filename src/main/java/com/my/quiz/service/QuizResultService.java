package com.my.quiz.service;

import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.repository.QuizResultRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizResultService {

    private QuizResultRepository quizResultRepository;


public QuizResultService(QuizResultRepository quizResultRepository){
    this.quizResultRepository = quizResultRepository;
}

public QuizResultEntity saveResult(QuizResultEntity quizResult){
    return quizResultRepository.save(quizResult);
}

public List<QuizResultEntity> getResultsByUser(Long userId){
    return quizResultRepository.findByUserId(userId);
}

public List<QuizResultEntity> getAllResults(){
    return quizResultRepository.findAll();
}

}

