// PlayQuiz.js
import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import './PlayQuiz.css';

const PlayQuiz = () => {
    const [quiz, setQuiz] = useState(null);
    const [userAnswers, setUserAnswers] = useState({ currentIndex: 0, answers: [] });
    const [showResults, setShowResults] = useState(false);
    const { id } = useParams();
    const QUIZ_API_BASE_URL = 'http://localhost:8080/api/quiz';

    useEffect(() => {
        (async function fetchQuiz() {
            try {
                const response = await axios.get(`${QUIZ_API_BASE_URL}/${id}`);
                setQuiz(response.data);
                setUserAnswers({ ...userAnswers, answers: new Array(response.data.questions.length).fill(null) });
            } catch (error) {
                console.error('Error fetching quiz:', error);
            }
        })();
    }, [id]);

    const handleAnswerChange = (questionIndex, answer) => {
        const newUserAnswers = [...userAnswers.answers];
        newUserAnswers[questionIndex] = answer;
        setUserAnswers({ ...userAnswers, answers: newUserAnswers });
    };

    const handleSubmit = () => {
        if (userAnswers.currentIndex === quiz.questions.length - 1) {
            setShowResults(true);
        } else {
            setUserAnswers({ ...userAnswers, currentIndex: userAnswers.currentIndex + 1 });
        }
    };

    const calculateScore = () => {
        return userAnswers.answers.reduce(
            (score, answer, index) => (quiz.questions[index].correctAnswer === answer ? score + 1 : score),
            0
        );
    };

    if (!quiz) {
        return <div>Loading...</div>;
    }

    if (showResults) {
        return (
            <div className="quiz-results">
                <h1>Results</h1>
                <p>
                    You scored {calculateScore()} out of {quiz.questions.length}
                </p>
                <Link to="/quizList">Back to quiz list</Link>
                <div className="result-question-container">
                    {quiz.questions.map((question, questionIndex) => {
                        const isUserCorrect = question.correctAnswer === userAnswers.answers[questionIndex];
                        return (
                            <div key={question.id} className="question">
                                <h3>
                                    {question.title}
                                    <span
                                        className={`result-indicator ${isUserCorrect ? 'correct' : 'incorrect'}`}
                                    >
                                {isUserCorrect ? 'Correct answer' : 'Incorrect answer'}
                            </span>
                                </h3>
                            <div className="answer-grid result-question-answer-grid">
                                {question.answerOptions.map((answer, answerIndex) => {
                                    const answerCode = String.fromCharCode(65 + answerIndex);
                                    const isCorrectAnswer = question.correctAnswer === answerCode;
                                    const isUserAnswer = userAnswers.answers[questionIndex] === answerCode;
                                    const isUserCorrect = isCorrectAnswer && isUserAnswer;

                                    let answerStyle = '';
                                    if (isUserCorrect) {
                                        answerStyle = 'correct-answer user-answer';
                                    } else if (isCorrectAnswer) {
                                        answerStyle = 'correct-answer';
                                    } else if (isUserAnswer) {
                                        answerStyle = 'incorrect-answer user-answer';
                                    }

                                    return (
                                        <div
                                            key={answerIndex}
                                            className={`answer-cell answer-${answerIndex} ${answerStyle}`}
                                        >
                                            {answer}
                                        </div>
                                    );
                                })}
                            </div>
                        </div>
                            );
                    })}
                </div>
            </div>
        );
    }


    else {
        const question = quiz.questions[userAnswers.currentIndex];
        return (
            <div className="play-quiz">
                <h2>{quiz.title}</h2>
                <p>{quiz.description}</p>
                <div key={question.id} className="question">
                    <h3>{question.title}</h3>
                    <div className="answer-grid">
                        {question.answerOptions.map((answer, answerIndex) => (
                            <label
                                key={answerIndex}
                                htmlFor={`question-${question.id}-answer-${answerIndex}`}
                                className={`answer-cell answer-${answerIndex} ${
                                    userAnswers.answers[userAnswers.currentIndex] === String.fromCharCode(65 + answerIndex)
                                        ? 'selected-answer'
                                        : ''
                                }`}
                            >
                                <input
                                    type="radio"
                                    id={`question-${question.id}-answer-${answerIndex}`}
                                    name={`question-${question.id}`}
                                    value={String.fromCharCode(65 + answerIndex)}
                                    onChange={() =>
                                        handleAnswerChange(userAnswers.currentIndex, String.fromCharCode(65 + answerIndex))
                                    }
                                    checked={
                                        userAnswers.answers[userAnswers.currentIndex] === String.fromCharCode(65 + answerIndex)
                                    }
                                />
                                {answer}
                            </label>
                        ))}
                    </div>
                </div>
                <button
                    onClick={handleSubmit}
                    disabled={userAnswers.answers[userAnswers.currentIndex] === null}
                >
                    {userAnswers.currentIndex === quiz.questions.length - 1 ? 'Submit' : 'Next'}
                </button>
            </div>
        );
    }
};

export default PlayQuiz;


