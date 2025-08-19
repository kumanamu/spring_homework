package com.my.quiz.service;


import com.my.quiz.repository.QuizRepository;

import java.util.Optional;

public class QuizService {
private QuizRepository quizRepository;

public QuizService(QuizRepository quizRepository){
    this.quizRepository = quizRepository;
}

public  Quiz addQuiz(Quiz quiz) {
    return quizRepository.save(quiz);

}

public  Quiz updateQuiz(Quiz quiz){
    return quizRepository.save(quiz);
}

public void deleteQuiz(Long quizId){
    quizRepository.deleteById(quizId);
}
public Optional<Quiz> getQuiz(Long quizId) {
return quizRepository.findById(quizId);

}
public List<Quiz> getAllQuizzes(){
    return quizRepository.findAll();
}
}
