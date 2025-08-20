package com.my.quiz.service;

import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    /** 문제 등록 */
    public QuizEntity createQuiz(QuizDto dto) {
        QuizEntity quiz = new QuizEntity();
        quiz.setQuestion(dto.getQuestion());
        quiz.setAnswer(dto.getAnswer());
        return quizRepository.save(quiz);
    }

    /** 문제 수정 */
    public boolean updateQuiz(Long id, QuizDto dto) {
        Optional<QuizEntity> quizOpt = quizRepository.findById(id);
        if (quizOpt.isPresent()) {
            QuizEntity quiz = quizOpt.get();
            quiz.setQuestion(dto.getQuestion());
            quiz.setAnswer(dto.getAnswer());
            quizRepository.save(quiz);
            return true;
        }
        return false;
    }

    /** 문제 삭제 */
    public boolean deleteQuiz(Long id) {
        if (quizRepository.existsById(id)) {
            quizRepository.deleteById(id);
            return true;
        }
        return false;
    }

    /** 모든 문제 조회 */
    public List<QuizEntity> getAllQuizzes() {
        return quizRepository.findAll();
    }



    /** 랜덤 문제 추출 */
    public QuizEntity getRandomQuiz() {
        List<QuizEntity> quizzes = quizRepository.findAll();
        if (quizzes.isEmpty()) return null;
        Random random = new Random();
        return quizzes.get(random.nextInt(quizzes.size()));
    }
}
