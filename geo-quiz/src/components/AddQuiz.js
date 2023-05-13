import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './AddQuiz.css'
import Modal from 'react-modal';

const AddQuiz = () => {
  const [quizTitle, setQuizTitle] = useState("");
  const [quizDescription, setQuizDescription] = useState("");
  const [quiz, setQuiz] = useState({
    title: '',
    description: '',
    questions: [],
  });
  const [loading, setLoading] = useState(false);
  const [quizAdded, setQuizAdded] = useState(false);
  const [addedQuizId, setAddedQuizId] = useState(null);
  const [modalIsOpen, setModalIsOpen] = useState(false);
  const [importQuizJson, setImportQuizJson] = useState('');
  const navigate = useNavigate();

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

const handleImportQuiz = async () => {
   try {
     await axios.post('http://localhost:8080/api/quiz/import', importQuizJson);
     setModalIsOpen(false);
     setImportQuizJson('');
   } catch (error) {
     alert('Error importing quiz. Please try again.');
   }
 };

  const handleQuestionChange = (questionIndex, field, value) => {
    const updatedQuestions = [...quiz.questions];
    updatedQuestions[questionIndex][field] = value;
    setQuiz({...quiz, questions: updatedQuestions});
  };

  const handleAnswerChange = (questionIndex, answerIndex, value) => {
    const updatedQuestions = [...quiz.questions];
    updatedQuestions[questionIndex].answerOptions[answerIndex] = value;
    setQuiz({...quiz, questions: updatedQuestions});
  };

  const handleTryQuiz = () => {
    navigate(`/quiz/${addedQuizId}`);
  };

  const handleAddAnotherQuiz = () => {
    setQuizAdded(false);
    setQuizTitle("");
    setQuizDescription("");
    setQuiz({
      title: '',
      description: '',
      questions: [],
    });
  };

  const handleGoToHomePage = () => {
    navigate('/');
  };

    const handleQuizSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);

        try {
            let quizJson;

            // Check if importQuizJson is not empty
            if (importQuizJson !== '') {
                quizJson = importQuizJson;
            } else {
                quizJson = JSON.stringify({
                    title: quizTitle,
                    description: quizDescription,
                    questions: quiz.questions,
                });
            }

            // Save the quiz
            const response = await axios.post("http://localhost:8080/api/quiz", quizJson);
            setLoading(false);
            setQuizAdded(true);
            setAddedQuizId(response.data.id);
            setModalIsOpen(false);
            setImportQuizJson('');
        } catch (error) {
            alert('Error adding quiz. Please try again.');
            setLoading(false);
        }
    };



    if (quizAdded) {
    return (
        <div className="quiz-added">
          <h2>Quiz added successfully!</h2>
          <button onClick={handleTryQuiz}>Try the quiz</button>
          <button onClick={handleAddAnotherQuiz}>Add another quiz</button>
          <button onClick={handleGoToHomePage}>Go back to homepage</button>
        </div>
    );
  } else {
    return (
        <div className="add-quiz">
         <button className="import-quiz-button" onClick={() => setModalIsOpen(true)}>Import Quiz</button>

          <h2>Add a New Quiz</h2>
          <form onSubmit={handleQuizSubmit}>
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
            <button type="submit" disabled={loading || quiz.questions.length === 0}>
              {loading ? 'Adding...' : 'Add Quiz'}
            </button>
          </form>
          <Modal
          isOpen={modalIsOpen}
          onRequestClose={() => setModalIsOpen(false)}
          contentLabel="Import Quiz"
          className="import-quiz-modal"
        >
          <h2>Import Quiz</h2>
          <textarea
            className="import-quiz-textarea"
            value={importQuizJson}
            onChange={(e) => setImportQuizJson(e.target.value)}
          />
          <button onClick={handleQuizSubmit}>Submit</button>
          <button onClick={() => setModalIsOpen(false)}>Cancel</button>
        </Modal>
        </div>
    );
  }
};

export default AddQuiz;
