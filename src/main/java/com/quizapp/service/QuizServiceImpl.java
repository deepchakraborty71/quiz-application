package com.quizapp.service;

import com.quizapp.dto.*;
import com.quizapp.entity.Option;
import com.quizapp.entity.Question;
import com.quizapp.entity.Quiz;
import com.quizapp.entity.QuizAttempt;
import com.quizapp.exception.ResourceNotFoundException;
import com.quizapp.repository.QuizAttemptRepository;
import com.quizapp.repository.QuizRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QuizServiceImpl implements QuizService {

    private final QuizRepository quizRepository;
    private final QuizAttemptRepository quizAttemptRepository;

    @Override
    public QuizResponse createQuiz(QuizRequest request) {
        Quiz quiz = Quiz.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .build();
        return QuizResponse.from(quizRepository.save(quiz));
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResponse> getAllQuizzes() {
        return quizRepository.findAll().stream()
                .map(QuizResponse::summary)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public QuizResponse getQuizById(Long quizId) {
        Quiz quiz = findQuizWithQuestions(quizId);
        return QuizResponse.from(quiz);
    }

    @Override
    public QuestionResponse addQuestion(Long quizId, QuestionRequest request) {
        Quiz quiz = quizRepository.findById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));

        long correctCount = request.getOptions().stream().filter(OptionRequest::isCorrect).count();
        if (correctCount != 1) {
            throw new IllegalArgumentException("Exactly one option must be marked as correct");
        }

        Question question = Question.builder().text(request.getText()).build();
        request.getOptions().forEach(optionRequest -> {
            Option option = Option.builder()
                    .text(optionRequest.getText())
                    .correct(optionRequest.isCorrect())
                    .build();
            question.addOption(option);
        });

        quiz.addQuestion(question);
        quizRepository.save(quiz);

        return QuestionResponse.from(question);
    }

    @Override
    public QuizResultResponse submitAnswers(Long quizId, SubmitAnswersRequest request) {
        Quiz quiz = findQuizWithQuestions(quizId);

        int total = quiz.getQuestions().size();
        int score = 0;

        for (Question question : quiz.getQuestions()) {
            Long chosenOptionId = request.getAnswers().get(question.getId());
            if (chosenOptionId == null) {
                continue;
            }
            boolean isCorrect = question.getOptions().stream()
                    .anyMatch(option -> option.getId().equals(chosenOptionId) && option.isCorrect());
            if (isCorrect) {
                score++;
            }
        }

        QuizAttempt attempt = QuizAttempt.builder()
                .quiz(quiz)
                .score(score)
                .totalQuestions(total)
                .build();
        quizAttemptRepository.save(attempt);

        double percentage = total == 0 ? 0.0 : (score * 100.0) / total;
        return new QuizResultResponse(attempt.getId(), quiz.getId(), score, total, percentage);
    }

    @Override
    @Transactional(readOnly = true)
    public List<QuizResultResponse> getAttemptsForQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new ResourceNotFoundException("Quiz not found with id: " + quizId);
        }
        return quizAttemptRepository.findByQuizIdOrderBySubmittedAtDesc(quizId).stream()
                .map(a -> new QuizResultResponse(
                        a.getId(),
                        quizId,
                        a.getScore(),
                        a.getTotalQuestions(),
                        a.getTotalQuestions() == 0 ? 0.0 : (a.getScore() * 100.0) / a.getTotalQuestions()))
                .toList();
    }

    @Override
    public void deleteQuiz(Long quizId) {
        if (!quizRepository.existsById(quizId)) {
            throw new ResourceNotFoundException("Quiz not found with id: " + quizId);
        }
        quizRepository.deleteById(quizId);
    }

    private Quiz findQuizWithQuestions(Long quizId) {
        return quizRepository.findWithQuestionsById(quizId)
                .orElseThrow(() -> new ResourceNotFoundException("Quiz not found with id: " + quizId));
    }
}
