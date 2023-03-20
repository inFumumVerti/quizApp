import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import QuizList from './components/QuizList';
import Quiz from './components/Quiz';
import AddQuiz from './components/AddQuiz';

function App() {
  return (
    <Router>
      <div>
        <nav>
          <ul>
            <li>
              <Link to="/">Home</Link>
            </li>
            <li>
              <Link to="/quizList">QuizList</Link>
            </li>
            <li>
              <Link to="/add-quiz">Add Quiz</Link>
            </li>
          </ul>
        </nav>

        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/quizList" element={<QuizList />} />
          <Route path="/quiz/:id" element={<Quiz />} />
          <Route path="/add-quiz" element={<AddQuiz />} />
        </Routes>
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
