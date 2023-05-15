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

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String QUESTIONS = "questions";
    private static final String CORRECT_ANSWER = "correctAnswer";
    private static final String ANSWER_OPTIONS = "answerOptions";

    public QuizImporter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public Quiz createQuizFromJson(String quizJson) throws IOException {
        String decodedQuizJson = URLDecoder.decode(quizJson, StandardCharsets.UTF_8.toString());
        JsonNode quizNode = objectMapper.readTree(decodedQuizJson);

        String title = getTextFromNode(quizNode, TITLE);
        String description = getTextFromNode(quizNode, DESCRIPTION);
        Quiz quiz = new Quiz(title, description);

        JsonNode questionsNode = quizNode.get(QUESTIONS);
        if (questionsNode == null) {
            throw new IllegalArgumentException("Quiz JSON must include 'questions' field.");
        }

        for (JsonNode questionNode : questionsNode) {
            Question question = createQuestionFromNode(questionNode);
            quiz.addQuestion(question);
        }

        return quiz;
    }

    private Question createQuestionFromNode(JsonNode questionNode) {
        String questionTitle = getTextFromNode(questionNode, TITLE);
        String correctAnswer = getTextFromNode(questionNode, CORRECT_ANSWER);
        List<String> answerOptions = objectMapper.convertValue(questionNode.get(ANSWER_OPTIONS), new TypeReference<List<String>>(){});

        if (!AnswerOption.isValid(correctAnswer)) {
            throw new IllegalArgumentException("Correct answer must be either A, B, C, or D.");
        }
        return new Question(questionTitle, correctAnswer, answerOptions.toArray(new String[0]));
    }

    private String getTextFromNode(JsonNode node, String field) {
        return node.get(field).asText();
    }
}

enum AnswerOption {
    A, B, C, D;
    public static boolean isValid(String value) {
        for (AnswerOption option : values()) {
            if (option.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
