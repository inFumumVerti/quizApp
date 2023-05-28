import React, { useState } from 'react';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
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

const [tabIndex, setTabIndex] = useState(0);
const [quizName, setQuizName] = useState("");
const [quizInfo, setQuizInfo] = useState("");
const [quizQuestions, setQuizQuestions] = useState(1);
const [quizLanguage, setQuizLanguage] = useState("");
const [quizAPIKey, setQuizAPIKey] = useState("");

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

  const handleGenerateQuiz = async (e) => {
      e.preventDefault();

      try {
          const response = await axios.post("http://localhost:8080/api/quiz/generate", null, {
              params: {
                  name: quizName,
                  info: quizInfo,
                  questions: quizQuestions,
                  language: quizLanguage,
                  apiKey: quizAPIKey
              }
          });

          setLoading(false);
          setQuizAdded(true);
          setAddedQuizId(response.data.id);
      } catch (error) {
          alert('Error generating quiz. Please try again.');
          setLoading(false);
      }
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
 <Tabs selectedIndex={tabIndex} onSelect={index => setTabIndex(index)}>
      <TabList>
        <Tab>Create Manually</Tab>
        <Tab>Create with AI</Tab>
        <Tab>Import Quiz from JSON</Tab>
      </TabList>

        <TabPanel>
            <div className="add-quiz">
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
                    </div>
        </TabPanel>

        <TabPanel>
            <h2>Create a New Quiz with AI</h2>
            <form onSubmit={handleGenerateQuiz}>
                <div>
                    <label htmlFor="quizName">Quiz Name: </label>
                    <input
                        type="text"
                        id="quizName"
                        value={quizName}
                        onChange={(e) => setQuizName(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="quizInfo">Further Information: </label>
                    <input
                        type="text"
                        id="quizInfo"
                        value={quizInfo}
                        onChange={(e) => setQuizInfo(e.target.value)}
                        required
                    />
                </div>
                <div>
                  <label htmlFor="quizQuestions">Number of Questions: </label>
                  <input
                      type="number"
                      id="quizQuestions"
                      value={quizQuestions}
                      onChange={(e) => setQuizQuestions(e.target.value)}
                      min="1"
                      max="10"
                      required
                  />
                </div>
                <div>
                    <label htmlFor="quizLanguage">Language: </label>
                    <input
                        type="text"
                        id="quizLanguage"
                        value={quizLanguage}
                        onChange={(e) => setQuizLanguage(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label htmlFor="quizAPIKey">OpenAI API Key: </label>
                    <input
                        type="text"
                        id="quizAPIKey"
                        value={quizAPIKey}
                        onChange={(e) => setQuizAPIKey(e.target.value)}
                        required
                    />
                </div>
                <button type="submit" disabled={loading}>
                    {loading ? 'Generating...' : 'Generate Quiz'}
                </button>
            </form>
        </TabPanel>
         <TabPanel>
                <div className="add-quiz">
                  <h2>Import Quiz</h2>
                  <textarea
                    className="import-quiz-textarea"
                    value={importQuizJson}
                    onChange={(e) => setImportQuizJson(e.target.value)}
                  />
                  <button onClick={handleQuizSubmit}>Submit</button>
                </div>
          </TabPanel>
    </Tabs>
    );
  }
};

export default AddQuiz;
