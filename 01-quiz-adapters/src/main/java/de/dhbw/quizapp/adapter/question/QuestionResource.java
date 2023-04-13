package de.dhbw.quizapp.adapter.question;

public class QuestionResource {
    private final Long id;

    private final String title;

    private final String correctAnswer;
    
    private final String[] answerOptions;



    public QuestionResource(final Long id, final String title, final String correctAnswer, final String[] answerOptions) {
        this.id = id;
        this.title = title;
        this.correctAnswer = correctAnswer;
        this.answerOptions = answerOptions;
    }



    public Long getId() {
        return this.id;
    }


    public String getTitle() {
        return this.title;
    }


    public String getCorrectAnswer() {
        return this.correctAnswer;
    }


    public String[] getAnswerOptions() {
        return this.answerOptions;
    }


}