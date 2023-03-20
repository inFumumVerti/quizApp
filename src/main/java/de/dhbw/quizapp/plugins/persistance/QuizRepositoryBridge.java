package de.dhbw.quizapp.plugins.persistance;

import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.domain.repository.quiz.QuizRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class QuizRepositoryBridge implements QuizRepository {

    private final SpringDataQuizRepository springDataQuizRepository;


    public QuizRepositoryBridge(final SpringDataQuizRepository springDataQuizRepository) {
        this.springDataQuizRepository = springDataQuizRepository;
    }

    @Override
    public Quiz save(final Quiz quiz) {
        return this.springDataQuizRepository.save(quiz);
    }

    @Override
    public Optional<Quiz> findById(Long id) {
        return this.springDataQuizRepository.findById(id);
    }

    @Override
    public List<Quiz> findAll() {
        return this.springDataQuizRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.springDataQuizRepository.deleteById(id);
    }
}
