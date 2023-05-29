import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { FiTrash2, FiDownload } from 'react-icons/fi';
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

    const deleteQuiz = async (id, title) => {
        if(window.confirm(`Are you sure you want to delete Quiz '${title}'?`)) {
            try {
                await axios.delete(`${QUIZ_API_BASE_URL}/${id}`);
                // After deletion, fetch the updated list of quizzes
                fetchQuizzes();
            } catch (error) {
                console.error('Error deleting quiz:', error);
            }
        }
    };


    const exportQuiz = (quiz) => {
        let quizCopy = {...quiz};
        // Remove the id property
        delete quizCopy.id;
        // Remove the id from each question
        quizCopy.questions = quizCopy.questions.map(question => {
            let questionCopy = {...question};
            delete questionCopy.id;
            return questionCopy;
        });
        const element = document.createElement("a");
        const file = new Blob([JSON.stringify(quizCopy)], {type: 'application/json'});
        element.href = URL.createObjectURL(file);
        element.download = `${quiz.title}.json`;
        document.body.appendChild(element); // Required for this to work in FireFox
        element.click();
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
                    <li key={quiz.id} style={{ position: 'relative' }}>
                        <Link to={`/quiz/${quiz.id}`}>
                            <h3>{quiz.title}</h3>
                        </Link>
                        <p>{quiz.description}</p>
                        <p>
                            <strong>Number of questions:</strong> {quiz.questions.length}
                        </p>
                        <button className="delete-button" onClick={() => deleteQuiz(quiz.id, quiz.title)}>
                            <FiTrash2 />
                        </button>
                        <button className="export-button" onClick={() => exportQuiz(quiz)}>
                            <FiDownload />
                        </button>
                    </li>
                ))}


            </ul>
        </div>
    );
};

export default QuizList;
