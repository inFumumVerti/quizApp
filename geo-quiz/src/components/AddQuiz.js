import React, { useState } from 'react';
import axios from 'axios';

const AddQuiz = () => {
  const [quizTitle, setQuizTitle] = useState('');
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setLoading(true);

    try {
      const response = await axios.post('http://localhost:5000/api/quiz', {
        title: quizTitle,
      });
      setQuizTitle('');
      setLoading(false);
      alert(`Quiz "${response.data.title}" added successfully!`);
    } catch (error) {
      setLoading(false);
      alert('Error adding quiz. Please try again.');
    }
  };

  return (
    <div>
      <h2>Add a New Quiz</h2>
      <form onSubmit={handleSubmit}>
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
        <button type="submit" disabled={loading}>
          {loading ? 'Adding...' : 'Add Quiz'}
        </button>
      </form>
    </div>
  );
};

export default AddQuiz;
