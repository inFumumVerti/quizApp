package de.dhbw.quizapp.domain.quiz;

import de.dhbw.quizapp.domain.question.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuizTest {

    private Quiz quiz;
    private Question question;

    @BeforeEach
    public void setUp() {
        quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setDescription("Test Description");

        question = new Question();
        question.setTitle("Test Question");
        question.setCorrectAnswer("Test Answer");
        question.setAnswerOptions(new String[]{"Test Answer", "Wrong Answer"});
    }

    @Test
    public void testGetId() {
        UUID id = quiz.getId();
        assertTrue(id instanceof UUID);
    }

    @Test
    public void testTitle() {
        assertEquals("Test Quiz", quiz.getTitle());
    }

    @Test
    public void testDescription() {
        assertEquals("Test Description", quiz.getDescription());
    }

    @Test
    public void testAddQuestion() {
        quiz.addQuestion(question);
        assertTrue(quiz.getQuestions().contains(question));
    }

    @Test
    public void testRemoveQuestion() {
        quiz.addQuestion(question);
        assertTrue(quiz.getQuestions().contains(question));

        quiz.removeQuestion(question);
        assertTrue(!quiz.getQuestions().contains(question));
    }
}
