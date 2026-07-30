package com.quizapp.controller;

import com.quizapp.dto.*;
import com.quizapp.service.QuizService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/quizzes")
@RequiredArgsConstructor
public class QuizController {

    private final QuizService quizService;

    @PostMapping
    public ResponseEntity<QuizResponse> createQuiz(@Valid @RequestBody QuizRequest request) {
        QuizResponse created = quizService.createQuiz(request);
        return ResponseEntity.created(URI.create("/api/quizzes/" + created.getId())).body(created);
    }

    @GetMapping
    public ResponseEntity<List<QuizResponse>> getAllQuizzes() {
        return ResponseEntity.ok(quizService.getAllQuizzes());
    }

    @GetMapping("/{quizId}")
    public ResponseEntity<QuizResponse> getQuiz(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getQuizById(quizId));
    }

    @DeleteMapping("/{quizId}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long quizId) {
        quizService.deleteQuiz(quizId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{quizId}/questions")
    public ResponseEntity<QuestionResponse> addQuestion(@PathVariable Long quizId,
                                                         @Valid @RequestBody QuestionRequest request) {
        QuestionResponse created = quizService.addQuestion(quizId, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PostMapping("/{quizId}/submit")
    public ResponseEntity<QuizResultResponse> submitAnswers(@PathVariable Long quizId,
                                                              @Valid @RequestBody SubmitAnswersRequest request) {
        return ResponseEntity.ok(quizService.submitAnswers(quizId, request));
    }

    @GetMapping("/{quizId}/attempts")
    public ResponseEntity<List<QuizResultResponse>> getAttempts(@PathVariable Long quizId) {
        return ResponseEntity.ok(quizService.getAttemptsForQuiz(quizId));
    }
}
