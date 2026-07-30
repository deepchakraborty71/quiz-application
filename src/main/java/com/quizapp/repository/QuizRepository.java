package com.quizapp.repository;

import com.quizapp.entity.Quiz;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuizRepository extends JpaRepository<Quiz, Long> {

    /** Fetches a quiz together with its questions and options in one query. */
    @EntityGraph(attributePaths = {"questions", "questions.options"})
    Optional<Quiz> findWithQuestionsById(Long id);
}
