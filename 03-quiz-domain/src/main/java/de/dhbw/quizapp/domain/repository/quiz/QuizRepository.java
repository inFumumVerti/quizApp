package de.dhbw.quizapp.domain.repository.quiz;

import de.dhbw.quizapp.domain.quiz.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface QuizRepository extends JpaRepository<Quiz, UUID> {
}
