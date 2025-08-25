// src/main/java/com/my/quiz/entity/UserAnswerEntity.java
package com.my.quiz.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "user_answers",
        indexes = {
                @Index(name="idx_user_answers_user", columnList = "user_id"),
                @Index(name="idx_user_answers_quiz", columnList = "quiz_id")
        })
public class UserAnswerEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "quiz_id")
    private QuizEntity quiz;

    /** 사용자가 선택한 값: true=O, false=X */
    @Column(nullable = false)
    private boolean choice;

    /** 맞았는지 여부 */
    @Column(nullable = false)
    private boolean correct;

    @CreationTimestamp
    @Column(nullable=false, updatable=false)
    private LocalDateTime createdAt;
}
