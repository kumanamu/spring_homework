package com.my.quiz.service;


import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.repository.QuizRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {
private QuizRepository quizRepository;

public QuizService(QuizRepository quizRepository){
    this.quizRepository = quizRepository;
}

public  QuizEntity addQuiz(QuizEntity quizEntity) {
    return quizRepository.save(quizEntity);

}

public QuizEntity updateQuiz(QuizEntity quizEntity){
    return quizRepository.save(quizEntity);
}

public void deleteQuiz(Long quizId){
    quizRepository.deleteById(quizId);
}
public Optional<QuizEntity> getQuiz(Long quizId) {
return quizRepository.findById(quizId);

}
public List<QuizEntity> getAllQuizzes(){
    return quizRepository.findAll();
}
}
