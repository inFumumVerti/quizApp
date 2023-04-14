import React from 'react';
import { useParams } from 'react-router-dom';
import PlayQuiz from './PlayQuiz';

const Quiz = () => {
    const { id } = useParams();

    return (
        <div>
            <PlayQuiz id={id} />
        </div>
    );
};

export default Quiz;