package de.dhbw.quizapp.adapter.quiz;

import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.domain.question.Question;
import de.dhbw.quizapp.application.quiz.QuizService;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class QuizImporter {

    private final QuizService quizService;
    private final ObjectMapper objectMapper;

    public QuizImporter(QuizService quizService, ObjectMapper objectMapper) {
        this.quizService = quizService;
        this.objectMapper = objectMapper;
    }

    public void importQuiz(String quizJson) throws IOException {
        JsonNode quizNode = objectMapper.readTree(quizJson);

        String title = quizNode.get("title").asText();
        String description = quizNode.get("description").asText();
        Quiz quiz = new Quiz();

        quiz.setTitle(title);
        quiz.setDescription(description);

        for (JsonNode questionNode : quizNode.get("questions")) {
            String questionTitle = questionNode.get("title").asText();
            String correctAnswer = questionNode.get("correctAnswer").asText();
            List<String> answerOptions = objectMapper.convertValue(questionNode.get("answerOptions"), new TypeReference<List<String>>(){});

            Question question = new Question(questionTitle, correctAnswer, answerOptions.toArray(new String[0]));
            quiz.addQuestion(question);
        }

        quizService.saveQuiz(quiz);
    }
}
