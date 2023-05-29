package de.dhbw.quizapp.application.question;

import de.dhbw.quizapp.domain.question.Question;
import de.dhbw.quizapp.domain.repository.question.QuestionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionRepository questionRepository;

    @InjectMocks
    private QuestionService questionService;

    private Question question;
    private Long questionId;

    @BeforeEach
    public void setUp() {
        questionId = 1L;
        question = new Question();
        question.setTitle("Test Question");
        question.setCorrectAnswer("Test Answer");
        question.setAnswerOptions(new String[]{"Test Answer", "Wrong Answer"});
    }

    @Test
    public void testSaveQuestion() {
        when(questionRepository.save(any(Question.class))).thenReturn(question);

        Question savedQuestion = questionService.saveQuestion(question);

        assertNotNull(savedQuestion);
        assertEquals(question.getTitle(), savedQuestion.getTitle());
        assertEquals(question.getCorrectAnswer(), savedQuestion.getCorrectAnswer());
    }

    @Test
    public void testFindQuestionById_found() {
        when(questionRepository.findById(any(Long.class))).thenReturn(Optional.of(question));

        Optional<Question> foundQuestion = questionService.findQuestionById(questionId);

        assertTrue(foundQuestion.isPresent());
        assertEquals(question.getTitle(), foundQuestion.get().getTitle());
    }

    @Test
    public void testFindQuestionById_notFound() {
        when(questionRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        Optional<Question> notFoundQuestion = questionService.findQuestionById(questionId);

        assertFalse(notFoundQuestion.isPresent());
    }

    @Test
    public void testFindAllQuestions() {
        when(questionRepository.findAll()).thenReturn(Arrays.asList(question));

        List<Question> questions = questionService.findAllQuestions();

        assertFalse(questions.isEmpty());
        assertEquals(1, questions.size());
        assertEquals(question.getTitle(), questions.get(0).getTitle());
    }
}
