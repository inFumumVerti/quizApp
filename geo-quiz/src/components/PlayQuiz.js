// PlayQuiz.js
import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import './PlayQuiz.css';

const PlayQuiz = () => {
    const [quiz, setQuiz] = useState(null);
    const [userAnswers, setUserAnswers] = useState({ currentIndex: 0, answers: [] });
    const [showResults, setShowResults] = useState(false);
    const [startTime, setStartTime] = useState(null);
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
            setStartTime(new Date().getTime());
        })();
    }, [id]);

    const handleAnswerChange = (questionIndex, answer) => {
        const newUserAnswers = [...userAnswers.answers];
        newUserAnswers[questionIndex] = answer;
        setUserAnswers({ ...userAnswers, answers: newUserAnswers });
    };

    const handleNavigation = (direction) => {
        const newCurrentIndex = direction === 'prev' ? userAnswers.currentIndex - 1 : userAnswers.currentIndex + 1;
        setUserAnswers({ ...userAnswers, currentIndex: newCurrentIndex });
    };

    const handleSubmit = async () => {
            if (userAnswers.currentIndex === quiz.questions.length - 1) {
                const endTime = new Date().getTime();
                const elapsedTime = (endTime - startTime) / 1000;
                try {
                    const response = await axios.post(`${QUIZ_API_BASE_URL}/${id}/submit`, userAnswers.answers);
                    handleQuizResponse(response.data);
                } catch (error) {
                    console.error('Error submitting quiz:', error);
                }
            } else {
                setUserAnswers({ ...userAnswers, currentIndex: userAnswers.currentIndex + 1 });
            }
        };

        const handleQuizResponse = (quizResponse) => {
            setShowResults(true);
            setUserAnswers((prevState) => ({
                ...prevState,
                results: quizResponse.questionResults,
                score: quizResponse.score,
            }));
        };

    if (!quiz) {
        return <div>Loading...</div>;
    }

    if (showResults) {
        return (
            <div className="quiz-results">
                <h1>Results</h1>
                <p>
                    You scored {userAnswers.score} out of {quiz.questions.length}
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
                <div className="question-counter">{`${userAnswers.currentIndex + 1} / ${quiz.questions.length}`}</div>
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
                <div className="button-container">
                    <button
                        onClick={() => handleNavigation('prev')}
                        disabled={userAnswers.currentIndex === 0}
                    >
                        Previous
                    </button>
                    {userAnswers.currentIndex < quiz.questions.length - 1 ? (
                        <button onClick={() => handleNavigation('next')}>
                            Next
                        </button>
                    ) : (
                        <button
                            onClick={handleSubmit}
                            disabled={userAnswers.answers.some(answer => answer === null)}
                        >
                            Submit
                        </button>

                    )}
                </div>

            </div>
        );
    }
};

export default PlayQuiz;


