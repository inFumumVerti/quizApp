package de.dhbw.quizapp.adapter.quiz;

import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.domain.question.Question;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.io.IOException;
import java.util.List;

@Component
public class QuizImporter {
    private final ObjectMapper objectMapper;

    public QuizImporter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Quiz createQuizFromJson(String quizJson) throws IOException {
        String decodedQuizJson = URLDecoder.decode(quizJson, StandardCharsets.UTF_8.toString());
        JsonNode quizNode = objectMapper.readTree(decodedQuizJson);

        String title = quizNode.get("title").asText();
        String description = quizNode.get("description").asText();
        Quiz quiz = new Quiz();

        quiz.setTitle(title);
        quiz.setDescription(description);

        for (JsonNode questionNode : quizNode.get("questions")) {
            String questionTitle = questionNode.get("title").asText();
            String correctAnswer = questionNode.get("correctAnswer").asText();
            List<String> answerOptions = objectMapper.convertValue(questionNode.get("answerOptions"), new TypeReference<List<String>>(){});

            // Check if correctAnswer is one of A, B, C or D
            if (!correctAnswer.equals("A") && !correctAnswer.equals("B") && !correctAnswer.equals("C") && !correctAnswer.equals("D")) {
                throw new IllegalArgumentException("Correct answer must be either A, B, C, or D.");
            }

            Question question = new Question(questionTitle, correctAnswer, answerOptions.toArray(new String[0]));
            quiz.addQuestion(question);
        }


        return quiz;
    }

}
