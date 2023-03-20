package de.dhbw.quizapp.adapter.quiz;

import java.util.function.Function;

import de.dhbw.quizapp.domain.quiz.Quiz;

public class QuizMapper implements Function<Quiz, QuizResource> {
    
    @Override
    public QuizResource apply(final Quiz quiz){
        return map(quiz);
    }

    private QuizResource map(final Quiz quiz){
        return new QuizResource(quiz.getId(), quiz.getTitle(), quiz.getDescription(), quiz.getQuestions());
    }

}
