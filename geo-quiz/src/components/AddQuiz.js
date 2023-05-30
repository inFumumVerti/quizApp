import React, { useState } from 'react';
import { Tab, Tabs, TabList, TabPanel } from 'react-tabs';
import 'react-tabs/style/react-tabs.css';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import './AddQuiz.css'
import CreateQuizManually from './CreateQuizManually';
import CreateQuizWithAI from './CreateQuizWithAI';
import ImportQuizFromJSON from './ImportQuizFromJSON';
import QuizAdded from './QuizAdded';
import Spinner from './Spinner';

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
  const [quizModel, setQuizModel] = useState("gpt-3.5-turbo");

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
        setLoading(true);

        try {
            const response = await axios.post("http://localhost:8080/api/quiz/generate", null, {
                          params: {
                              name: quizName,
                              info: quizInfo,
                              questions: quizQuestions,
                              language: quizLanguage,
                              apiKey: quizAPIKey,
                              model: quizModel,
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
            <QuizAdded
              handleTryQuiz={handleTryQuiz}
              handleAddAnotherQuiz={handleAddAnotherQuiz}
              handleGoToHomePage={handleGoToHomePage}
            />
          );
        }
        else if (loading){
        return <Spinner />
        }
        else {
          return (
          <Tabs selectedIndex={tabIndex} onSelect={index => setTabIndex(index)}>
              <TabList>
                <Tab>Create Manually</Tab>
                <Tab>Create with AI</Tab>
                <Tab>Import Quiz from JSON</Tab>
              </TabList>
              <TabPanel>
                <CreateQuizManually
                  quizTitle={quizTitle}
                  setQuizTitle={setQuizTitle}
                  quizDescription={quizDescription}
                  setQuizDescription={setQuizDescription}
                  quiz={quiz}
                  setQuiz={setQuiz}
                  loading={loading}
                  handleQuizSubmit={handleQuizSubmit}
                  handleQuestionChange={handleQuestionChange}
                  handleAnswerChange={handleAnswerChange}
                  addQuestion={addQuestion}
                  removeQuestion={removeQuestion}
                />
              </TabPanel>
              <TabPanel>
                <CreateQuizWithAI
                  loading={loading}
                  handleGenerateQuiz={handleGenerateQuiz}
                  quizName={quizName}
                  setQuizName={setQuizName}
                  quizInfo={quizInfo}
                  setQuizInfo={setQuizInfo}
                  quizQuestions={quizQuestions}
                  setQuizQuestions={setQuizQuestions}
                  quizLanguage={quizLanguage}
                  setQuizLanguage={setQuizLanguage}
                  quizAPIKey={quizAPIKey}
                  setQuizAPIKey={setQuizAPIKey}
                  quizModel={quizModel}
                  setQuizModel={setQuizModel}
                />
              </TabPanel>
              <TabPanel>
                <ImportQuizFromJSON
                  importQuizJson={importQuizJson}
                  setImportQuizJson={setImportQuizJson}
                  handleQuizSubmit={handleQuizSubmit}
                />
              </TabPanel>
            </Tabs>
          );
        }
      };

      export default AddQuiz;