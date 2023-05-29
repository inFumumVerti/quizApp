package de.dhbw.quizapp.application.quiz;

import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.domain.repository.quiz.QuizRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizRepository quizRepository;

    @InjectMocks
    private QuizService quizService;

    private Quiz quiz;
    private UUID quizId;

    @BeforeEach
    public void setUp() {
        quizId = UUID.randomUUID();
        quiz = new Quiz();
        quiz.setTitle("Test Quiz");
    }

    @Test
    public void testSaveQuiz() {
        when(quizRepository.save(any(Quiz.class))).thenReturn(quiz);

        Quiz savedQuiz = quizService.saveQuiz(quiz);

        assertNotNull(savedQuiz);
        assertEquals(quiz.getTitle(), savedQuiz.getTitle());
    }

    @Test
    public void testFindQuizById_found() {
        when(quizRepository.findById(any(UUID.class))).thenReturn(Optional.of(quiz));

        Quiz foundQuiz = quizService.findQuizById(quizId);

        assertNotNull(foundQuiz);
        assertEquals(quiz.getTitle(), foundQuiz.getTitle());
    }

    @Test
    public void testFindQuizById_notFound() {
        when(quizRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(QuizNotFoundException.class, () -> {
            quizService.findQuizById(quizId);
        });
    }
}
