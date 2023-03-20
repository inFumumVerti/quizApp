package de.dhbw.quizapp.domain.quiz;

import de.dhbw.quizapp.domain.question.Question;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

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

    @ManyToMany
    private List<Question> questions;

    protected Quiz() {
        // default constructor for JPA
    }

    public Quiz(@NotBlank final String title, @NotBlank final String description) {
        this.id = UUID.randomUUID();
        this.title = title;
        this.description = description;
        this.questions = new ArrayList<>();
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

    public void addQuestion(@Nonnull final Question question) {
        this.questions.add(question);
    }

    public void removeQuestion(@Nonnull final Question question) {
        this.questions.remove(question);
    }
}
