package com.my.quiz.service;

import com.my.quiz.entity.QuizEntity;
import com.my.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    // 모든 문제 조회
    public List<QuizEntity> getAll() {
        return quizRepository.findAll();
    }

    // 문제 추가
    public QuizEntity addQuiz(String question, String answer) {
        QuizEntity quiz = new QuizEntity();
        quiz.setQuestion(question);
        quiz.setAnswer(answer);
        return quizRepository.save(quiz);
    }

    // 문제 수정
    public boolean updateQuiz(Long id, String question, String answer) {
        return quizRepository.findById(id).map(quiz -> {
            quiz.setQuestion(question);
            quiz.setAnswer(answer);
            quizRepository.save(quiz);
            return true;
        }).orElse(false);
    }

    // 문제 삭제
    public boolean deleteQuiz(Long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // 랜덤 문제 n개 가져오기
    public List<QuizEntity> getRandom(int count) {
        List<QuizEntity> quizzes = quizRepository.findAll();
        Collections.shuffle(quizzes);
        return quizzes.stream().limit(count).collect(Collectors.toList());
    }
}
