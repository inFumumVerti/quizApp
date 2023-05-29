# QuizApp

## Getting Started

To get started, follow these instructions:

1. Navigate into the `geo-quiz` directory.
2. Run `npm i` to install all necessary dependencies for the React app.
3. Run `npm start` to start the application.

## Features

Currently, the application has three major features:

1. **List of Current Quizzes**: Displays a list of all quizzes currently in the database. The list can be filtered using a search bar. Each entry can be deleted or exported to a JSON file.
2. **Playing a Quiz**: A selected quiz can be played and the score is determined based on the time taken to finish the quiz as well as the number of correct answers. Currently, the scoring allocates half of the available points for correct answers and the remaining half for correct answers made in less than five seconds on average over the entirety of the quiz. The scoring mechanism will be revised in future updates.
3. **Adding a New Quiz**: A new quiz can be added in one of three ways:
    a. Manually add a quiz using the quiz creator tool.
    b. Import a quiz by uploading a compatible JSON file.
    c. Use OpenAI's API to create a quiz. Simply provide the topic, the number of questions, and the desired model, and the AI will return a quiz that is automatically added to the database. 
    Hint: Try the GPT3.5-Turbo mode first, as many people do not have access to the GPT4 version and will therefore run into errors trying to create a quiz that way.

## Planned Features

- Map-based quizzes
- Interactivity between players
- User management
