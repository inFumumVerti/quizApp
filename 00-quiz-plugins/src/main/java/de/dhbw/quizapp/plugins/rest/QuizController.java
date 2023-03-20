package de.dhbw.quizapp.plugins.rest;

import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.application.quiz.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Quiz getQuiz(@PathVariable("id") Long id) {
        return quizService.findQuizById(id).orElse(null);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Quiz> getAllQuizzes() {
        return quizService.findAllQuizzes();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteQuiz(@PathVariable("id") Long id) {
        quizService.deleteQuizById(id);
    }
}
