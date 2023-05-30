import React from 'react';

const CreateQuizManually = ({
  quizTitle,
  setQuizTitle,
  quizDescription,
  setQuizDescription,
  quiz,
  setQuiz,
  loading,
  handleQuizSubmit,
  handleQuestionChange,
  handleAnswerChange,
  addQuestion,
  removeQuestion
}) => {
  const optionIndicesToLetters = ['A', 'B', 'C', 'D'];

  const isFormValid = () => {
      if (quizTitle === "" || quizDescription === "" || quiz.questions.length === 0) {
        return false;
      }
      for (let question of quiz.questions) {
        if (question.title === "" || question.correctAnswer === "") {
          return false;
        }
        for (let option of question.answerOptions) {
          if (option === "") {
            return false;
          }
        }
      }
      return true;
    };

    return (
      <div>
        <label>Quiz Title</label>
        <input type="text" value={quizTitle} onChange={e => setQuizTitle(e.target.value)} required />

        <label>Quiz Description</label>
        <input type="text" value={quizDescription} onChange={e => setQuizDescription(e.target.value)} required />

        {quiz.questions.map((question, index) => (
          <div key={index}>
            <label>Question</label>
            <input type="text" value={question.title} onChange={e => handleQuestionChange(index, 'title', e.target.value)} required />

            {question.answerOptions.map((option, optIndex) => (
              <div key={optIndex}>
                <label>Option {optionIndicesToLetters[optIndex]}</label>
                <input type="text" value={option} onChange={e => handleAnswerChange(index, optIndex, e.target.value)} required />
              </div>
            ))}

            <label>Correct Answer</label>
            <select value={question.correctAnswer} onChange={e => handleQuestionChange(index, 'correctAnswer', e.target.value)} required>
              <option value="">Select...</option>
              <option value="A">Option A</option>
              <option value="B">Option B</option>
              <option value="C">Option C</option>
              <option value="D">Option D</option>
            </select>

            <button onClick={() => removeQuestion(index)}>Remove Question</button>
          </div>
        ))}

        <button onClick={addQuestion}>Add Question</button>

        {loading ? (
          <p>Loading...</p>
        ) : (
         <button onClick={handleQuizSubmit} disabled={!isFormValid()}>Submit Quiz</button>
        )}
      </div>
    );
  };

  export default CreateQuizManually;
