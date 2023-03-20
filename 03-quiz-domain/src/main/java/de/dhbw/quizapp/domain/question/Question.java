package de.dhbw.quizapp.domain.question;

import io.micrometer.common.lang.NonNull;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

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
    @Column(name = "description")
    private String description;

    @NonNull
    @Column(name = "question_type")
    private QuestionType questionType;

    @NotBlank
    @Column(name = "correct_answer")
    private String correctAnswer;

    @NotBlank
    @Column(name = "answer_options")
    private String[] answerOptions;

    protected Question() {
        // default constructor for JPA
    }

    public Question(@NotBlank final String title, @NotBlank final String description,
                    @NonNull final QuestionType questionType, @NotBlank final String correctAnswer,
                    @NotBlank final String[] answerOptions) {
        this.title = title;
        this.description = description;
        this.questionType = questionType;
        this.correctAnswer = correctAnswer;
        this.answerOptions = answerOptions;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    public void setQuestionType(final QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(final String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public String[] getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(final String[] answerOptions) {
        this.answerOptions = answerOptions;
    }

    public boolean checkAnswer(final String givenAnswer) {
        return correctAnswer.equalsIgnoreCase(givenAnswer);
    }

}
