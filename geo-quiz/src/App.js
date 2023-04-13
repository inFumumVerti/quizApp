import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import QuizList from './components/QuizList';
import Quiz from './components/Quiz';
import AddQuiz from './components/AddQuiz';
import './App.css';

function App() {
  return (
    <Router>
      <nav>
        <ul className="top-bar">
          <li>
            <Link to="/">geoo!</Link>
          </li>
          <li>
            <Link to="/quizList">QuizList</Link>
          </li>
          <li>
            <Link to="/add-quiz">Add Quiz</Link>
          </li>
        </ul>
      </nav>

      <div className="content">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/quizList" element={<QuizList />} />
          <Route path="/quiz/:id" element={<Quiz />} />
          <Route path="/add-quiz" element={<AddQuiz />} />
        </Routes>
      </div>

      <div className="bottom-bar">
        <p>&copy; 2023 Quiz App</p>
      </div>
    </Router>
  );
}

const Home = () => {
  return (
    <div>
      <h1>Welcome to the Quiz App</h1>
      <p>Test your knowledge with our quizzes!</p>
    </div>
  );
};

export default App;
