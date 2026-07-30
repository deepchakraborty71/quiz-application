package com.quizapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuizResultResponse {

    private Long attemptId;
    private Long quizId;
    private int score;
    private int totalQuestions;
    private double percentage;
}
