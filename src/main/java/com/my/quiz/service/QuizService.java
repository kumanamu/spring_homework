package com.my.quiz.service;


import com.my.quiz.dto.QuizDto;
import com.my.quiz.repository.QuizRepository;

import java.util.List;
import java.util.Optional;

public class QuizService {
private QuizRepository quizRepository;

public QuizService(QuizRepository quizRepository){
    this.quizRepository = quizRepository;
}

public  QuizDto addQuiz(QuizDto quizDto) {
    return quizRepository.save(quizDto);

}

public  QuizDto updateQuiz(QuizDto quizDto){
    return quizRepository.save(quizDto);
}

public void deleteQuiz(Long quizId){
    quizRepository.deleteById(quizId);
}
public Optional<QuizDto> getQuiz(Long quizId) {
return quizRepository.findById(quizId);

}
public List<QuizDto> getAllQuizzes(){
    return quizRepository.findAll();
}
}
