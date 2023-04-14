import React, { useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import axios from 'axios';
import './PlayQuiz.css';

const PlayQuiz = () => {
    const [quiz, setQuiz] = useState(null);
    const [userAnswers, setUserAnswers] = useState([]);
    const [showResults, setShowResults] = useState(false);
    const { id } = useParams();
    const QUIZ_API_BASE_URL = 'http://localhost:8080/api/quiz';

    useEffect(() => {
        (async function fetchQuiz() {
            try {
                alert("aaa");
                const response = await axios.get(`${QUIZ_API_BASE_URL}/${id}`);
                alert("aaaaa");
                alert(JSON.stringify(response.data, null, 2)); // Add this line
                setQuiz(response.data);
                setUserAnswers(new Array(response.data.questions.length).fill(null));
            } catch (error) {
                console.error('Error fetching quiz:', error);
            }
        })();
    }, [id]);

    const handleAnswerChange = (questionIndex, answer) => {
        const newUserAnswers = [...userAnswers];
        newUserAnswers[questionIndex] = answer;
        setUserAnswers(newUserAnswers);
    };

    const handleSubmit = () => {
        setShowResults(true);
    };

    const calculateScore = () => {
        return userAnswers.reduce(
            (score, answer, index) =>
                quiz.questions[index].correctAnswer === answer ? score + 1 : score,
            0
        );
    };

    if (!quiz) {
        return <div>Loading...</div>;
    }

    if (showResults) {
        return (
            <div className="quiz-results">
                <h2>Results</h2>
                <p>
                    You scored {calculateScore()} out of {quiz.questions.length}
                </p>
                <Link to="/quizList">Back to quiz list</Link>
            </div>
        );
    } else {
        return (
            <div className="play-quiz">
                <h2>{quiz.title}</h2>
                <p>{quiz.description}</p>
                {quiz.questions.map((question, questionIndex) => (
                    <div key={question.id} className="question">
                        <h3>{question.title}</h3>
                        <ul>
                            {question.answerOptions.map((answer, answerIndex) => (
                                <li key={answerIndex}>
                                    <input
                                        type="radio"
                                        id={`question-${question.id}-answer-${answerIndex}`}
                                        name={`question-${question.id}`}
                                        value={String.fromCharCode(65 + answerIndex)}
                                        onChange={() =>
                                            handleAnswerChange(questionIndex, String.fromCharCode(65 + answerIndex))
                                        }
                                        checked={
                                            userAnswers[questionIndex] ===
                                            String.fromCharCode(65 + answerIndex)
                                        }
                                    />
                                    <label htmlFor={`question-${question.id}-answer-${answerIndex}`}>
                                        {answer}
                                    </label>
                                </li>
                            ))}
                        </ul>
                    </div>
                ))}
                <button onClick={handleSubmit}>Submit</button>
            </div>
        );
    }
};

export default PlayQuiz;
