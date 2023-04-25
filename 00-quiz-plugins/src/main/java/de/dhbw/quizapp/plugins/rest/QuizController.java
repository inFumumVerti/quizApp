package de.dhbw.quizapp.plugins.rest;

import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.application.quiz.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.saveQuiz(quiz);
    }

    @GetMapping(value = "/{id}")
    public Quiz getQuiz(@PathVariable("id") UUID id) {
        return quizService.findQuizById(id);
    }

    @GetMapping(value = "/search")
    public List<Quiz> searchQuizzesByName(@RequestParam("name") String name) {
        return quizService.findQuizzesByName(name);
    }

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.findAllQuizzes();
    }

    @DeleteMapping(value = "/{id}")
    public void deleteQuiz(@PathVariable("id") UUID id) {
        quizService.deleteQuizById(id);
    }

    @PostMapping(value = "/{id}/submit")
    public Map<String, Object> submitQuiz(@PathVariable("id") UUID id, @RequestBody List<String> userAnswers) {
        return quizService.evaluateQuiz(id, userAnswers);
    }

}
