import React, { useState } from 'react';
import axios from 'axios';
import './AddQuiz.css'

const AddQuiz = () => {
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
          description: '',
          questionType: '',
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
      const response = await axios.post('http://localhost:8080/api/quiz', quiz);
      setQuiz({
        title: '',
        description: '',
        questions: [],
      });
      setLoading(false);
      alert(`Quiz "${response.data.title}" added successfully!`);
    } catch (error) {
      setLoading(false);
      alert('Error adding quiz. Please try again.');
    }
  };

  return (
      <div>
        <h2>Add a New Quiz</h2>
        <form onSubmit={handleSubmit}>
          <div>
            <label htmlFor="quizTitle">Quiz Title: </label>
            <input
                type="text"
                id="quizTitle"
                value={quiz.title}
                onChange={(e) => setQuiz({ ...quiz, title: e.target.value })}
                required
            />
          </div>
          <div>
            <label htmlFor="quizDescription">Quiz Description: </label>
            <textarea
                id="quizDescription"
                value={quiz.description}
                onChange={(e) => setQuiz({ ...quiz, description: e.target.value })}
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
                  <label htmlFor={`questionDescription-${questionIndex}`}>Description: </label>
                  <textarea
                      id={`questionDescription-${questionIndex}`}
                      value={question.description}
                      onChange={(e) =>
                          handleQuestionChange(questionIndex, 'description', e.target.value)
                      }
                      required
                  />
                </div>
                <div>
                  <label htmlFor={`questionType-${questionIndex}`}>Type: </label>
                  <select
                      id={`questionType-${questionIndex}`}
                      value={question.questionType}
                      onChange={(e) =>
                          handleQuestionChange(questionIndex, 'questionType', e.target.value)
                      }
                      required
                  >
                    <option value="">Select Question Type</option>
                    {/* Add more options for question types based on your backend */}
                  </select>
                </div>
                <div>
                  <label htmlFor={`correctAnswer-${questionIndex}`}>Correct Answer: </label>
                  <input
                      type="text"
                      id={`correctAnswer-${questionIndex}`}
                      value={question.correctAnswer}
                      onChange={(e) =>
                          handleQuestionChange(questionIndex, 'correctAnswer', e.target.value)
                      }
                      required
                  />
                </div>
                {question.answerOptions.map((answer, answerIndex) => (
                    <div key={answerIndex}>
                      <label htmlFor={`answerOption-${questionIndex}-${answerIndex}`}>
                        Answer {answerIndex + 1}:
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
