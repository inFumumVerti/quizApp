package de.dhbw.quizapp.adapter.question;

import de.dhbw.quizapp.domain.question.QuestionType;

public class QuestionResource {
    private final Long id;

    private final String title;

    private final String description;
   
    private final QuestionType questionType;

    private final String correctAnswer;
    
    private final String[] answerOptions;



    public QuestionResource(final Long id, final String title, final String description, final QuestionType questionType, final String correctAnswer, final String[] answerOptions) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.questionType = questionType;
        this.correctAnswer = correctAnswer;
        this.answerOptions = answerOptions;
    }



    public Long getId() {
        return this.id;
    }


    public String getTitle() {
        return this.title;
    }


    public String getDescription() {
        return this.description;
    }


    public QuestionType getQuestionType() {
        return this.questionType;
    }


    public String getCorrectAnswer() {
        return this.correctAnswer;
    }


    public String[] getAnswerOptions() {
        return this.answerOptions;
    }


}