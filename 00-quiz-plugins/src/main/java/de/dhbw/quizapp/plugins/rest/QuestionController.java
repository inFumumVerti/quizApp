package de.dhbw.quizapp.plugins.rest;

import de.dhbw.quizapp.domain.question.Question;
import de.dhbw.quizapp.application.question.QuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/question")
@CrossOrigin(origins = "http://localhost:3000")
public class QuestionController {
    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Question createQuestion(@RequestBody Question question) {
        return questionService.saveQuestion(question);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Question getQuestion(@PathVariable Long id) {
        return questionService.findQuestionById(id).orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Question> getAllQuestions() {
        return questionService.findAllQuestions();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteQuestion(@PathVariable Long id) {
        questionService.deleteQuestionById(id);
    }
}
