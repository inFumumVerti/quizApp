import React, { useState } from 'react';

const Question = ({ question }) => {
  const [selectedOption, setSelectedOption] = useState(null);

  const handleOptionChange = (e) => {
    setSelectedOption(e.target.value);
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Perform any necessary actions with the selected option (e.g., check if it's correct)
  };

  return (
    <div>
      <h2>{question.title}</h2>
      <p>{question.description}</p>
      <form onSubmit={handleSubmit}>
        {question.options.map((option) => (
          <div key={option.id}>
            <label>
              <input
                type="radio"
                value={option.id}
                checked={selectedOption === option.id}
                onChange={handleOptionChange}
              />
              {option.text}
            </label>
          </div>
        ))}
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default Question;
