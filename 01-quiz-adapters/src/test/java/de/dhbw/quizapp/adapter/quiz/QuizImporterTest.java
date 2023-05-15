package de.dhbw.quizapp.adapter.quiz;

import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.domain.question.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.fasterxml.jackson.databind.ObjectMapper;

public class QuizImporterTest {
    private QuizImporter quizImporter;

    @BeforeEach
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        quizImporter = new QuizImporter(objectMapper);
    }

    @Test
    public void testCreateQuizFromValidJson() {
        String json = "{\"title\": \"Test Quiz\", \"description\": \"This is a test quiz.\", \"questions\": [{\"title\": \"Test Question\", \"correctAnswer\": \"A\", \"answerOptions\": [\"Option A\", \"Option B\", \"Option C\", \"Option D\"]}]}";

        try {
            Quiz quiz = quizImporter.createQuizFromJson(json);

            assertEquals("Test Quiz", quiz.getTitle());
            assertEquals("This is a test quiz.", quiz.getDescription());
            assertEquals(1, quiz.getQuestions().size());

            Question question = quiz.getQuestions().get(0);
            assertEquals("Test Question", question.getTitle());
            assertEquals("A", question.getCorrectAnswer());
            assertEquals(4, question.getAnswerOptions().length);

        } catch (Exception e) {
            fail("Exception should not be thrown: " + e.getMessage());
        }
    }

    @Test
    public void testCreateQuizFromEmptyJson() {
        String json = "{}";

        assertThrows(IllegalArgumentException.class, () -> {
            quizImporter.createQuizFromJson(json);
        });
    }

    @Test
    public void testCreateQuizFromJsonWithoutQuestions() {
        String json = "{\"title\": \"Test Quiz\", \"description\": \"This is a test quiz.\"}";

        assertThrows(IllegalArgumentException.class, () -> {
            quizImporter.createQuizFromJson(json);
        });
    }

    @Test
    public void testCreateQuizFromJsonWithInvalidCorrectAnswer() {
        String json = "{\"title\": \"Test Quiz\", \"description\": \"This is a test quiz.\", \"questions\": [{\"title\": \"Test Question\", \"correctAnswer\": \"Z\", \"answerOptions\": [\"Option A\", \"Option B\", \"Option C\", \"Option D\"]}]}";

        assertThrows(IllegalArgumentException.class, () -> {
            quizImporter.createQuizFromJson(json);
        });
    }

    @Test
    public void testCreateQuizFromJsonWithEmptyQuestion() {
        String json = "{\"title\": \"Test Quiz\", \"description\": \"This is a test quiz.\", \"questions\": [{}]}";

        assertThrows(IllegalArgumentException.class, () -> {
            quizImporter.createQuizFromJson(json);
        });
    }
}
