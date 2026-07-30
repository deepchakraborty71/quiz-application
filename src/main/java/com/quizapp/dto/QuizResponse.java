package com.quizapp.dto;

import com.quizapp.entity.Quiz;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private List<QuestionResponse> questions;

    public static QuizResponse from(Quiz quiz) {
        List<QuestionResponse> questions = quiz.getQuestions().stream()
                .map(QuestionResponse::from)
                .toList();
        return new QuizResponse(quiz.getId(), quiz.getTitle(), quiz.getDescription(),
                quiz.getCreatedAt(), questions);
    }

    /** Lightweight summary used in list endpoints (skips the question payload). */
    public static QuizResponse summary(Quiz quiz) {
        return new QuizResponse(quiz.getId(), quiz.getTitle(), quiz.getDescription(),
                quiz.getCreatedAt(), List.of());
    }
}
