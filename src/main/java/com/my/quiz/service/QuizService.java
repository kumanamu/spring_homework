package com.my.quiz.service;


import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class QuizService {
    private final QuizRepository quizRepository;

    public QuizEntity createQuiz(QuizDto dto) {
        QuizEntity quiz = new QuizEntity();
        quiz.setQuestion(dto.getQuestion());
        quiz.setAnswer(dto.getAnswer());
        return quizRepository.save(quiz);
    }

    public QuizEntity updateQuiz(Long id, QuizDto dto) {
        return quizRepository.findById(id).map(q -> {
            q.setQuestion(dto.getQuestion());
            q.setAnswer(dto.getAnswer());
            return quizRepository.save(q);
        }).orElse(null);
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }

    public List<QuizEntity> getRandomQuizzes(int count) {
        List<QuizEntity> all = quizRepository.findAll();
        Random rand = new Random();
        return all.stream().sorted((a,b)->rand.nextInt(3)-1).limit(count).toList();
    }
}