package com.my.quiz.service;

import com.my.quiz.entity.QuizEntity;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.repository.QuizRepository;
import com.my.quiz.repository.QuizResultRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuizResultService {
    private final QuizResultRepository resultRepository;
    private final QuizRepository quizRepository;


    public QuizResultService(QuizResultRepository resultRepository, QuizRepository quizRepository) {
        this.resultRepository = resultRepository;
        this.quizRepository = quizRepository;
    }


    public List<QuizResultEntity> getAllResults(){
        return resultRepository.findAll();
    }


    public List<QuizResultEntity> getResultsByUser(Long userId){
        return resultRepository.findByUserId(userId);
    }


    public void saveResults(Long userId, List<Long> quizIds, List<String> answers){
// quizIds와 answers는 같은 인덱스라고 가정
        List<QuizResultEntity> toSave = new ArrayList<>();
        for(int i=0;i<quizIds.size();i++){
            Long qid = quizIds.get(i);
            String userAns = answers.get(i);
            QuizEntity quiz = quizRepository.findById(qid).orElse(null);
            if(quiz == null) continue;
            boolean correct = quiz.getAnswer() != null && quiz.getAnswer().trim().equalsIgnoreCase(userAns.trim());
            int score = correct ? 1 : 0;


            QuizResultEntity result = QuizResultEntity.builder()
                    .quizId(qid)
                    .quizQuestion(quiz.getQuestion())
                    .correctAnswer(quiz.getAnswer())
                    .userAnswer(userAns)
                    .score(score)
                    .userId(userId)
                    .correct(correct)
                    .createdAt(LocalDateTime.now())
                    .build();
            toSave.add(result);
        }
        resultRepository.saveAll(toSave);
    }
}

