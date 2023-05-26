package de.dhbw.quizapp.application.quiz;

import de.dhbw.quizapp.domain.Score.Score;
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

    public Map<String, Object> evaluateQuiz(UUID id, List<String> userAnswers, int timeTaken) {
        Quiz quiz = findQuizById(id);

        int correctAnswers = 0;
        int totalQuestions = quiz.getQuestions().size();
        int totalTimeAllowed = totalQuestions * 5; // 5 seconds per question

        List<Map<String, String>> questionResults = new ArrayList<>();

        for (int i = 0; i < totalQuestions; i++) {
            Question question = quiz.getQuestions().get(i);
            String userAnswer = userAnswers.get(i);
            boolean isCorrect = question.getCorrectAnswer().equals(userAnswer);

            if (isCorrect) {
                correctAnswers++;
            }

            Map<String, String> questionResult = new HashMap<>();
            questionResult.put("questionId", question.getId().toString());
            questionResult.put("userAnswer", userAnswer);
            questionResult.put("correctAnswer", question.getCorrectAnswer());
            questionResult.put("isCorrect", Boolean.toString(isCorrect));
            questionResults.add(questionResult);
        }

        Score score = new Score(correctAnswers, totalQuestions, timeTaken, totalTimeAllowed);

        Map<String, Object> result = new HashMap<>();
        result.put("score", score.getValue());
        result.put("questionResults", questionResults);

        return result;
    }
}
