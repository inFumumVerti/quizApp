package de.dhbw.quizapp.application.quiz;

import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.domain.repository.quiz.QuizRepository;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
public class QuizService {

    private final QuizRepository quizRepository;
 
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz saveQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public Quiz findQuizById(UUID id) {
        return quizRepository.findById(id).orElseThrow(() -> new QuizNotFoundException("Quiz not found with ID: " + id));
    }

    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }

    public void deleteQuizById(UUID id) {
        quizRepository.deleteById(id);
    }
}
