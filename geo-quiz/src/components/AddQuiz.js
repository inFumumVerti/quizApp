import React, { useState } from 'react';
import axios from 'axios';
import './AddQuiz.css'

const AddQuiz = () => {
  const [quizTitle, setQuizTitle] = useState("");
  const [quizDescription, setQuizDescription] = useState("");
  const [questions] = useState([]);
  const [quiz, setQuiz] = useState({
    title: '',
    description: '',
    questions: [],
  });
  const [loading, setLoading] = useState(false);

  const addQuestion = () => {
    setQuiz({
      ...quiz,
      questions: [
        ...quiz.questions,
        {
          title: '',
          correctAnswer: '',
          answerOptions: ['', '', '', ''],
        },
      ],
    });
  };

  const removeQuestion = (questionIndex) => {
    setQuiz({
      ...quiz,
      questions: quiz.questions.filter((_, index) => index !== questionIndex),
    });
  };

  const handleQuestionChange = (questionIndex, field, value) => {
    const updatedQuestions = [...quiz.questions];
    updatedQuestions[questionIndex][field] = value;
    setQuiz({ ...quiz, questions: updatedQuestions });
  };

  const handleAnswerChange = (questionIndex, answerIndex, value) => {
    const updatedQuestions = [...quiz.questions];
    updatedQuestions[questionIndex].answerOptions[answerIndex] = value;
    setQuiz({ ...quiz, questions: updatedQuestions });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
        // Save questions first
        const savedQuestions = await Promise.all(
            questions.map(async (question) => {
              const response = await axios.post(
                  "http://localhost:8080/api/question",
                  question
              );
              return response.data;
            })
        );

        // Save the quiz with the saved questions
        const response = await axios.post("http://localhost:8080/api/quiz", {
          title: quizTitle,
          description: quizDescription,
          questions: savedQuestions,
        });
      setLoading(false);
      alert(`Quiz "${response.data.title}" added successfully!`);
    } catch (error) {
      setLoading(false);
      alert('Error adding quiz. Please try again.');
    }
  };

  return (
      <div className="add-quiz">
        <h2>Add a New Quiz</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="quizTitle">Quiz Title: </label>
            <input
                type="text"
                id="quizTitle"
                value={quizTitle}
                onChange={(e) => setQuizTitle(e.target.value)}
                required
            />
          </div>
          <div>
            <label htmlFor="quizDescription">Quiz Description: </label>
            <input
                type="text"
                id="quizDescription"
                value={quizDescription}
                onChange={(e) => setQuizDescription(e.target.value)}
                required
            />
          </div>
          {quiz.questions.map((question, questionIndex) => (
              <div key={questionIndex} className="question">
                <h3>Question {questionIndex + 1}</h3>
                <div>
                  <label htmlFor={`questionTitle-${questionIndex}`}>Title: </label>
                  <input
                      type="text"
                      id={`questionTitle-${questionIndex}`}
                      value={question.title}
                      onChange={(e) =>
                          handleQuestionChange(questionIndex, 'title', e.target.value)
                      }
                      required
                  />
                </div>
                <div>
                  <label htmlFor={`correctAnswer-${questionIndex}`}>Correct Answer: </label>
                  <select
                      id={`correctAnswer-${questionIndex}`}
                      value={question.correctAnswer}
                      onChange={(e) =>
                          handleQuestionChange(questionIndex, 'correctAnswer', e.target.value)
                      }
                      required
                  >
                    <option value="">Select Correct Answer</option>
                    {question.answerOptions.map((_, answerIndex) => (
                        <option value={String.fromCharCode(65 + answerIndex)} key={answerIndex}>
                          {String.fromCharCode(65 + answerIndex)}
                        </option>
                    ))}
                  </select>
                </div>
                {question.answerOptions.map((answer, answerIndex) => (
                    <div key={answerIndex}>
                      <label htmlFor={`answerOption-${questionIndex}-${answerIndex}`}>
                        Answer {String.fromCharCode(65 + answerIndex)}:
                      </label>
                      <input
                          type="text"
                          id={`answerOption-${questionIndex}-${answerIndex}`}
                          value={answer}
                          onChange={(e) =>
                              handleAnswerChange(questionIndex, answerIndex, e.target.value)
                          }
                          required
                      />
                    </div>
                ))}
                <button type="button" onClick={() => removeQuestion(questionIndex)}>
                  Remove Question
                </button>
              </div>
          ))}
          <button type="button" onClick={addQuestion}>Add Question</button>
          <button type="submit" disabled={loading}>
            {loading ? 'Adding...' : 'Add Quiz'}
          </button>
        </form>
      </div>
  );
};

export default AddQuiz;
