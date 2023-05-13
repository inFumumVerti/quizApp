package de.dhbw.quizapp.plugins.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.dhbw.quizapp.application.question.QuestionService;
import de.dhbw.quizapp.domain.question.Question;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(QuestionController.class)
public class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private QuestionService questionService;

    private ObjectMapper objectMapper;
    private Question question;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        question = new Question();
        question.setTitle("Test Question");
        question.setCorrectAnswer("Test Answer");
        question.setAnswerOptions(new String[]{"Test Answer", "Wrong Answer"});
    }

    @Test
    public void testCreateQuestion() throws Exception {
        when(questionService.saveQuestion(any(Question.class))).thenReturn(question);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/question")
                        .content(objectMapper.writeValueAsString(question))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(question.getTitle()))
                .andExpect(jsonPath("$.correctAnswer").value(question.getCorrectAnswer()));
    }

    @Test
    public void testGetQuestion() throws Exception {
        Long questionId = 1L;
        when(questionService.findQuestionById(any(Long.class))).thenReturn(Optional.of(question));

        mockMvc.perform(MockMvcRequestBuilders.get("/api/question/" + questionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value(question.getTitle()))
                .andExpect(jsonPath("$.correctAnswer").value(question.getCorrectAnswer()));
    }

    @Test
    public void testGetAllQuestions() throws Exception {
        List<Question> questions = Arrays.asList(question);
        when(questionService.findAllQuestions()).thenReturn(questions);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/question"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value(question.getTitle()))
                .andExpect(jsonPath("$[0].correctAnswer").value(question.getCorrectAnswer()));
    }
}
