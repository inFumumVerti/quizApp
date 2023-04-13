package de.dhbw.quizapp.adapter.question;

import java.util.function.Function;

import de.dhbw.quizapp.domain.question.Question;

public class QuestionMapper implements Function<Question, QuestionResource> {
    
    @Override
    public QuestionResource apply(final Question question){
        return map(question);
    }

    private QuestionResource map(final Question question){
        return new QuestionResource(question.getId(), question.getTitle(), question.getCorrectAnswer(), question.getAnswerOptions());
    }

}
