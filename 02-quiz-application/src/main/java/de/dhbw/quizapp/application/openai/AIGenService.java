package de.dhbw.quizapp.application.openai;

import com.theokanning.openai.completion.chat.ChatCompletionRequest;
import com.theokanning.openai.completion.chat.ChatMessage;
import com.theokanning.openai.completion.chat.ChatMessageRole;
import com.theokanning.openai.service.OpenAiService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Service
public class AIGenService {
    private String openaiApiKey;
    private OpenAiService openAiService;

    public AIGenService() {
        this.openaiApiKey = System.getenv("OPENAI_TOKEN");
        if (this.openaiApiKey != null) {
            Duration timeOutDuration = Duration.ofSeconds(60);
            this.openAiService = new OpenAiService(openaiApiKey, timeOutDuration);
        }
    }

    public void setApiKey(String apiKey) {
        this.openaiApiKey = apiKey;
        Duration timeOutDuration = Duration.ofSeconds(60);
        this.openAiService = new OpenAiService(openaiApiKey, timeOutDuration);
    }

    public String generateQuiz(String quizName, String furtherInfo, int noOfQuestions, String language) {
        if (openAiService == null) {
            throw new IllegalStateException("API key not set");
        }

        String prompt = createPrompt(quizName, furtherInfo, noOfQuestions, language);
        List<ChatMessage> messages = createChatMessages(prompt);
        ChatCompletionRequest chatCompletionRequest = createChatCompletionRequest(messages);

        return openAiService.createChatCompletion(chatCompletionRequest).getChoices().get(0).getMessage().getContent();
    }

    private String createPrompt(String quizName, String furtherInfo, int noOfQuestions, String language) {
        String promptTemplate = """
Create a quiz using the following information:
{
    "title": "%s",
    "description": "%s",
    "number of questions": "%d",
    "language of questions": "%s",
    "example question and answer": "Example question: <exampleQuestion>, Example answer: <exampleAnswer>"
}
Use this exact format:
{
    "title": "<QuizName>",
    "description": "<description>",
    "questions": [
        {
            "title": "<question1>",
            "correctAnswer": "<Correct Answer = A | B | C | D>",
            "answerOptions": [
                "<answerOption1.A>, <answerOption1.B>, <answerOption1.C>, <answerOption1.D>"
            ]
        },
        // more questions...
    ]
}

Example:

Quiz in JSON-Format:
{
    "title": "European Capitals",
    "description": "A quiz to test knowledge of the capitals of various European countries.",
    "questions": [
        {
            "title": "What is the capital of Germany?",
            "correctAnswer": "C",
            "answerOptions": ["Paris", "London", "Berlin", "Rome"]
        },
        // more questions...
    ]
}
""";
        return String.format(promptTemplate, quizName, furtherInfo, noOfQuestions, language);
    }

    private List<ChatMessage> createChatMessages(String prompt) {
        List<ChatMessage> messages = new ArrayList<>();
        ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), prompt);
        messages.add(systemMessage);

        return messages;
    }

    private ChatCompletionRequest createChatCompletionRequest(List<ChatMessage> messages) {
        return ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .n(1)
                .maxTokens(500)
                .build();
    }
}