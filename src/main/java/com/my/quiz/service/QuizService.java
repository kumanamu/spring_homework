package com.my.quiz.service;


import com.my.quiz.dto.QuizDto;
import com.my.quiz.entity.QuizEntity;
import com.my.quiz.entity.QuizResultEntity;
import com.my.quiz.repository.QuizRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
    public class QuizService {
        private final QuizRepository quizRepository;


        public QuizService(QuizRepository quizRepository) {
            this.quizRepository = quizRepository;
        }


        public List<QuizEntity> getAllQuizzes() {
            return quizRepository.findAll();
        }


        public QuizEntity addQuiz(QuizEntity quizEntity) {
            return quizRepository.save(quizEntity);
        }


        public void deleteQuiz(Long id) {
            quizRepository.deleteById(id);
        }


        // DTO 변환 (선택 사용)
        public List<QuizDto> toDtoList(List<QuizEntity> list){
            return list.stream().map(this::toDto).collect(Collectors.toList());
        }


        public QuizDto toDto(QuizEntity e){
            if(e == null) return null;
            return QuizDto.builder()
                    .id(e.getId())
                    .question(e.getQuestion())
                    .answer(e.getAnswer())
                    .options(e.getOptions())
                    .build();
        }


        public QuizEntity toEntity(QuizDto d){
            if(d == null) return null;
            return QuizEntity.builder()
                    .id(d.getId())
                    .question(d.getQuestion())
                    .answer(d.getAnswer())
                    .options(d.getOptions())
                    .build();
        }
    }
