import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import './QuizList.css';
import axios from "axios";

const QuizList = () => {
    const [quizzes, setQuizzes] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const QUIZ_API_BASE_URL = "http://localhost:8080/api/quiz";

    useEffect(() => {
        fetchQuizzes();
    }, []);

    const fetchQuizzes = async (name) => {
        try {
            const response = name
                ? await axios.get(`${QUIZ_API_BASE_URL}/search`, { params: { name } })
                : await axios.get(QUIZ_API_BASE_URL);
            setQuizzes(response.data);
        } catch (error) {
            console.error('Error fetching quizzes:', error);
        }
    };

    const handleSearch = (e) => {
        e.preventDefault();
        fetchQuizzes(searchTerm);
    };

    return (
        <div className="quiz-list">
            <h2>Available Quizzes</h2>
            <form onSubmit={handleSearch}>
                <input
                    type="text"
                    placeholder="Search quizzes by name"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                />
            </form>
            <ul>
                {quizzes.map((quiz) => (
                    <li key={quiz.id}>
                        <Link to={`/quiz/${quiz.id}`}>
                            <h3>{quiz.title}</h3>
                        </Link>
                        <p>{quiz.description}</p>
                        <p>
                            <strong>Number of questions:</strong> {quiz.questions.length}
                        </p>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default QuizList;
