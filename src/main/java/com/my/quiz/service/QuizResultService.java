package com.my.quiz.service;

import com.my.quiz.repository.QuizResultRepository;

import java.util.List;

public class QuizResultService {

    private QuizResultRepository quizResultRepository;


public QuizResultService(QuizResultRepository quizResultRepository){
    this.quizResultRepository = quizResultRepository;
}

public QuizResult saveResult(QuizResult quizResult){
    return quizResultRepository.save(quizResult);
}

public List<QuizResult> getResultsByuser(Long userId){
    return quizResultRepository.findByUserId(userId);
}

public List<QuizResult> getAllResults(){
    return quizResultRepository.findAll();
}

}
