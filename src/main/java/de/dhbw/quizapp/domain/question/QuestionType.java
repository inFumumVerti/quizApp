package de.dhbw.quizapp.domain.question;

public enum QuestionType {
    TEXT("Text"),
    MULTIPLE_CHOICE("Multiple Choice"),
    MAP("Map");

    private final String displayName;

    QuestionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
