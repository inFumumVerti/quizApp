package de.dhbw.quizapp.plugins.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dhbw.quizapp.adapter.quiz.QuizImporter;
import de.dhbw.quizapp.application.quiz.QuizService;
import de.dhbw.quizapp.domain.quiz.Quiz;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class QuizControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuizService quizService;

    @MockBean
    private QuizImporter quizImporter;

    private ObjectMapper objectMapper;
    private Quiz quiz;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        quiz = new Quiz();
        quiz.setTitle("Test Quiz");
        quiz.setDescription("Test Description");
    }

    @Test
    public void testCreateQuiz() throws Exception {
        when(quizImporter.createQuizFromJson(anyString())).thenReturn(quiz);
        when(quizService.saveQuiz(any(Quiz.class))).thenReturn(quiz);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/quiz")
                        .content(objectMapper.writeValueAsString(quiz))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(quiz.getTitle()))
                .andExpect(jsonPath("$.description").value(quiz.getDescription()));
    }

    @Test
    public void testGetQuiz() throws Exception {
        UUID quizId = UUID.randomUUID();
        when(quizService.findQuizById(any(UUID.class))).thenReturn(quiz);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz/" + quizId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(quiz.getTitle()))
                .andExpect(jsonPath("$.description").value(quiz.getDescription()));
    }

    @Test
    public void testSearchQuizzesByName() throws Exception {
        String name = "Test Quiz";
        List<Quiz> quizzes = Arrays.asList(quiz);
        when(quizService.findQuizzesByName(anyString())).thenReturn(quizzes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz/search?name=" + name))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(quiz.getTitle()))
                .andExpect(jsonPath("$[0].description").value(quiz.getDescription()));
    }

    @Test
    public void testGetAllQuizzes() throws Exception {
        List<Quiz> quizzes = Arrays.asList(quiz);
        when(quizService.findAllQuizzes()).thenReturn(quizzes);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/quiz"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(quiz.getTitle()))
                .andExpect(jsonPath("$[0].description").value(quiz.getDescription()));
    }
}
