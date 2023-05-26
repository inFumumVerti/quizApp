package de.dhbw.quizapp.application.quiz;

import de.dhbw.quizapp.domain.question.Question;
import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.domain.repository.quiz.QuizRepository;

import java.util.*;

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

    public List<Quiz> findQuizzesByName(String name) {
        return quizRepository.findByTitle(name);
    }

    public List<Quiz> findAllQuizzes() {
        return quizRepository.findAll();
    }

    public void deleteQuizById(UUID id) {
        quizRepository.deleteById(id);
    }

    public Map<String, Object> evaluateQuiz(UUID id, List<String> userAnswers, double elapsedTime) {
        Quiz quiz = findQuizById(id);
        int score = 0;
        List<Map<String, String>> questionResults = new ArrayList<>();

        for (int i = 0; i < quiz.getQuestions().size(); i++) {
            Question question = quiz.getQuestions().get(i);
            String userAnswer = userAnswers.get(i);
            boolean isCorrect = question.getCorrectAnswer().equals(userAnswer);

            if (isCorrect) {
                score++;
            }

            Map<String, String> questionResult = new HashMap<>();
            questionResult.put("questionId", question.getId().toString());
            questionResult.put("userAnswer", userAnswer);
            questionResult.put("correctAnswer", question.getCorrectAnswer());
            questionResult.put("isCorrect", Boolean.toString(isCorrect));
            questionResults.add(questionResult);
        }
        double pointsCorrect = ((double) score / quiz.getQuestions().size()) * 50;
        double pointsTime = Math.max(0, Math.min(50, 50 * ((5 * quiz.getQuestions().size() - elapsedTime) / (5 * quiz.getQuestions().size()))));
        int totalScore = (int) (pointsCorrect + pointsTime);

        Map<String, Object> result = new HashMap<>();
        result.put("score", totalScore);
        result.put("questionResults", questionResults);

        return result;
    }


}
