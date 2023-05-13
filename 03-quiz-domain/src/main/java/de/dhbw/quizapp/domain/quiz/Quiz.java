package de.dhbw.quizapp.domain.quiz;

import de.dhbw.quizapp.domain.question.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private UUID id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "description")
    private String description;

    @ManyToMany(cascade = CascadeType.ALL)
    @NotEmpty
    private List<Question> questions = new ArrayList<>();

    public Quiz() {
        // default constructor for JPA
    }

    public UUID getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void addQuestion(@NotNull final Question question) {
        this.questions.add(question);
    }

    public void removeQuestion(@NotNull final Question question) {
        this.questions.remove(question);
    }
}
