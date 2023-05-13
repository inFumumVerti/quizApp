package de.dhbw.quizapp.domain.question;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class QuestionTest {

    private Question question;

    @BeforeEach
    public void setUp() {
        String[] answerOptions = {"Test Answer", "Wrong Answer"};
        question = new Question("Test Question", "Test Answer", answerOptions);
    }

    @Test
    public void testGetId() {
        Long id = question.getId();
        assertTrue(id instanceof Long);
    }

    @Test
    public void testTitle() {
        assertEquals("Test Question", question.getTitle());
    }

    @Test
    public void testCorrectAnswer() {
        assertEquals("Test Answer", question.getCorrectAnswer());
    }

    @Test
    public void testAnswerOptions() {
        String[] expectedOptions = {"Test Answer", "Wrong Answer"};
        assertArrayEquals(expectedOptions, question.getAnswerOptions());
    }

    @Test
    public void testCheckAnswer() {
        assertTrue(question.checkAnswer("Test Answer"));
        assertFalse(question.checkAnswer("Wrong Answer"));
    }
}
