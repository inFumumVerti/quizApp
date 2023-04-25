package de.dhbw.quizapp.plugins.rest;

import de.dhbw.quizapp.application.question.QuestionService;
import de.dhbw.quizapp.domain.question.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/question")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public Question createQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @GetMapping(value = "/{id}")
    public Question getQuestion(@PathVariable Long id) {
        return questionService.findQuestionById(id).orElse(null);
    }

    @GetMapping
    public List<Question> getAllQuestions() {
        return questionService.findAllQuestions();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
    }
}
