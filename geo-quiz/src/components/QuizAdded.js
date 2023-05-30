import React from 'react';

const QuizAdded = ({ handleTryQuiz, handleAddAnotherQuiz, handleGoToHomePage }) => {
    return (
        <div className="quiz-added">
          <h2>Quiz added successfully!</h2>
          <button onClick={handleTryQuiz}>Try the quiz</button>
          <button onClick={handleAddAnotherQuiz}>Add another quiz</button>
          <button onClick={handleGoToHomePage}>Go back to homepage</button>
        </div>
    );
};
export default QuizAdded;
