package de.dhbw.quizapp.plugins.persistance;

import de.dhbw.quizapp.domain.quiz.Quiz;
import java.util.UUID;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpringDataQuizRepository extends JpaRepository<Quiz, UUID> {
    Optional<Quiz> findById(Long id);

    void deleteById(Long id);
}
