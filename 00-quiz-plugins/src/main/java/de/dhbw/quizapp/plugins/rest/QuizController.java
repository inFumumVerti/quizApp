package de.dhbw.quizapp.plugins.rest;

import de.dhbw.quizapp.adapter.quiz.QuizImporter;
import de.dhbw.quizapp.domain.quiz.Quiz;
import de.dhbw.quizapp.application.quiz.QuizService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/quiz")
@CrossOrigin(origins = "http://localhost:3000")
public class QuizController {

    private final QuizService quizService;
    private final QuizImporter quizImporter;

    @Autowired
    public QuizController(QuizService quizService, QuizImporter quizImporter) {
        this.quizService = quizService;
        this.quizImporter = quizImporter;
    }

    @PostMapping
    public Quiz createQuiz(@RequestBody String quizJson) {
        try {
            Quiz quiz = quizImporter.createQuizFromJson(quizJson);
            return quizService.saveQuiz(quiz);
        } catch (IOException e) {
            throw new RuntimeException("Error creating quiz: " + e.getMessage(), e);
        }
    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable("id") UUID id) {
        Quiz quiz = quizService.findQuizById(id);
        return quiz != null ? ResponseEntity.ok(quiz) : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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
    public Map<String, Object> submitQuiz(@PathVariable("id") UUID id, @RequestBody Map<String, Object> body) {
        List<String> userAnswers = (List<String>) body.get("userAnswers");
        double elapsedTime = (double) body.get("elapsedTime");
        return quizService.evaluateQuiz(id, userAnswers, elapsedTime);
    }



}
