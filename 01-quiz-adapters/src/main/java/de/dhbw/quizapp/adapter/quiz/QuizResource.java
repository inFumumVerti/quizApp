package de.dhbw.quizapp.adapter.quiz;

import java.util.List;
import java.util.UUID;

import de.dhbw.quizapp.domain.question.Question;

public class QuizResource {
    
    private final UUID id;

    private final String title;

    private final String description;

    private final List<Question> questions;

    public QuizResource(final UUID id, final String title, final String description, final List<Question> questions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questions = questions;
    }

    public UUID getId() {
        return this.id;
    }


    public String getTitle() {
        return this.title;
    }


    public String getDescription() {
        return this.description;
    }


    public List<Question> getQuestions() {
        return this.questions;
    }
}
