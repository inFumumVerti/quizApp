package de.dhbw.quizapp.domain.quiz;

import de.dhbw.quizapp.domain.question.Question;

import java.util.ArrayList;
import java.util.List;

public class Quiz {

    private Long id;
    private String title;
    private String description;
    private List<Question> questions;

    public Quiz(Long id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public void addQuestion(Question question) {
        this.questions.add(question);
    }

    public void removeQuestion(Question question) {
        this.questions.remove(question);
    }
}
