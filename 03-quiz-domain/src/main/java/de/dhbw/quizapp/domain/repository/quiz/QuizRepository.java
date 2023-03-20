package de.dhbw.quizapp.domain.repository.quiz;

import de.dhbw.quizapp.domain.quiz.Quiz;

import java.util.List;
import java.util.Optional;

public interface QuizRepository {
    Quiz save(Quiz quiz);

    Optional<Quiz> findById(Long id);

    List<Quiz> findAll();

    void deleteById(Long id);
}
