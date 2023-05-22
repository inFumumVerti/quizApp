package de.dhbw.quizapp.domain.question;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "question")
public class Question {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "title")
    private String title;

    @NotBlank
    @Column(name = "correct_answer")
    private String correctAnswer;

    @NotBlank
    @Column(name = "answer_options")
    private String answerOptions;

    public Question() {
        // default constructor for JPA
    }

    public Question(@NotBlank final String title,
                    @NotBlank final String correctAnswer,
                    @NotBlank final String[] answerOptions) {
        this.title = title;
        this.correctAnswer = correctAnswer;
        this.answerOptions = String.join("|", answerOptions);
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(final String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getAnswerOptions() {
        return answerOptions.split("\\|");
    }

    public void setAnswerOptions(final String[] answerOptions) {
        this.answerOptions = String.join("|", answerOptions);
    }

    public boolean checkAnswer(final String givenAnswer) {
        return correctAnswer.equalsIgnoreCase(givenAnswer);
    }
}
