package de.dhbw.quizapp.domain.repository.question;

import de.dhbw.quizapp.domain.question.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    Question save(Question question);

    Optional<Question> findById(Long id);

    List<Question> findAll();

    void deleteById(Long id);
}
