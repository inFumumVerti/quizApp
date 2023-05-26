package de.dhbw.quizapp.domain.Score;

import java.util.Objects;

public class Score {
    private final int value;

    public Score(int correctAnswers, int totalQuestions, int timeTaken, int totalTimeAllowed) {
        double correctPoints = ((double) correctAnswers / totalQuestions) * 50;
        double timePoints = ((double) correctAnswers / totalQuestions) *(Math.max(0, Math.min(50, 50 * ((totalTimeAllowed - timeTaken) / (double) totalTimeAllowed))));
        this.value = (int) Math.floor(correctPoints + timePoints);
    }

    public int getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Score score = (Score) o;
        return value == score.value;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
