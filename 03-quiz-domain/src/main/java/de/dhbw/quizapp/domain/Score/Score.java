package de.dhbw.quizapp.domain.Score;

import java.util.Objects;

public class Score {
    private final int value;

    public Score(int value) {
        if (value < 0 || value > 100) {
            throw new IllegalArgumentException("Score must be between 0 and 100.");
        }
        this.value = value;
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
