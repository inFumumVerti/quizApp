import React from 'react';

const CreateQuizWithAI = ({
  loading,
  handleGenerateQuiz,
  quizName,
  setQuizName,
  quizInfo,
  setQuizInfo,
  quizQuestions,
  setQuizQuestions,
  quizLanguage,
  setQuizLanguage,
  quizAPIKey,
  setQuizAPIKey,
  quizModel,
  setQuizModel,
}) => {
  return (
    <div className="createQuizWithAI">
      <h2>Create a Quiz with AI</h2>
      <form onSubmit={handleGenerateQuiz}>
        <div className="form-group">
          <label>Quiz Name:</label>
          <input
            type="text"
            value={quizName}
            onChange={(e) => setQuizName(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Quiz Info:</label>
          <input
            type="text"
            value={quizInfo}
            onChange={(e) => setQuizInfo(e.target.value)}
          />
        </div>
        <div className="form-group">
            <label>Number of Questions:</label>
            <input
                type="number"
                min="1"
                max="8"
                value={quizQuestions}
                onChange={(e) => {
                    const val = Math.min(8, Math.max(1, e.target.value));
                    setQuizQuestions(val);
                }}
            />
        </div>
        <div className="form-group">
          <label>Language:</label>
          <input
            type="text"
            value={quizLanguage}
            onChange={(e) => setQuizLanguage(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>API Key:</label>
          <input
            type="text"
            value={quizAPIKey}
            onChange={(e) => setQuizAPIKey(e.target.value)}
          />
        </div>
        <div className="form-group">
          <label>Model:</label>
          <select id="quizModel" value={quizModel} onChange={(e) => setQuizModel(e.target.value)} required>
                  <option value="gpt-4">GPT-4</option>
                  <option value="gpt-4-32k">GPT-4 32K</option>
                  <option value="gpt-3.5-turbo">GPT-3.5 Turbo</option>
          </select>
        </div>
        <button type="submit">Generate Quiz</button>
      </form>
    </div>
  );
};

export default CreateQuizWithAI;
