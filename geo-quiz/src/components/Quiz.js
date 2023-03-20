import React, { useState, useEffect } from 'react';
import axios from 'axios';
import Question from './Question';

const Quiz = ({ match }) => {
  const [quiz, setQuiz] = useState(null);

  useEffect(() => {
    const fetchQuiz = async () => {
      const response = await axios.get(`/api/quiz/${match.params.id}`);
      setQuiz(response.data);
    };
    fetchQuiz();
  }, [match.params.id]);

  if (!quiz) {
    return <div>Loading...</div>;
  }

  return (
    <div>
      <h1>{quiz.title}</h1>
      <p>{quiz.description}</p>
      {quiz.questions.map((question) => (
        <Question key={question.id} question={question} />
      ))}
    </div>
  );
};

export default Quiz;
