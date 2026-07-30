package com.quizapp.service;

import com.quizapp.dto.*;

import java.util.List;

public interface QuizService {

    QuizResponse createQuiz(QuizRequest request);

    List<QuizResponse> getAllQuizzes();

    QuizResponse getQuizById(Long quizId);

    QuestionResponse addQuestion(Long quizId, QuestionRequest request);

    QuizResultResponse submitAnswers(Long quizId, SubmitAnswersRequest request);

    List<QuizResultResponse> getAttemptsForQuiz(Long quizId);

    void deleteQuiz(Long quizId);
}
