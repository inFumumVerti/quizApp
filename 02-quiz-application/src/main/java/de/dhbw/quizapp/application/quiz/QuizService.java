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

        int totalQuestions = quiz.getQuestions().size();
        int totalTimeAllowed = totalQuestions * 5; // 5 seconds per question

        List<Map<String, String>> questionResults = getQuestionResults(quiz, userAnswers);
        int correctAnswers = (int) questionResults.stream().filter(result -> Boolean.parseBoolean(result.get("isCorrect"))).count();

        Score score = calculateScore(correctAnswers, totalQuestions, timeTaken, totalTimeAllowed);

        return buildResult(score, questionResults);
    }

    private Score calculateScore(int correctAnswers, int totalQuestions, int timeTaken, int totalTimeAllowed) {
        return new Score(correctAnswers, totalQuestions, timeTaken, totalTimeAllowed);
    }

    private List<Map<String, String>> getQuestionResults(Quiz quiz, List<String> userAnswers) {
        List<Map<String, String>> questionResults = new ArrayList<>();
        List<Question> questions = quiz.getQuestions();

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            String userAnswer = userAnswers.get(i);
            boolean isCorrect = question.getCorrectAnswer().equals(userAnswer);

            Map<String, String> questionResult = new HashMap<>();
            questionResult.put("questionId", question.getId().toString());
            questionResult.put("userAnswer", userAnswer);
            questionResult.put("correctAnswer", question.getCorrectAnswer());
            questionResult.put("isCorrect", Boolean.toString(isCorrect));
            questionResults.add(questionResult);
        }

        return questionResults;
    }

    private Map<String, Object> buildResult(Score score, List<Map<String, String>> questionResults) {
        Map<String, Object> result = new HashMap<>();
        result.put("score", score.getValue());
        result.put("questionResults", questionResults);

        return result;
    }
}