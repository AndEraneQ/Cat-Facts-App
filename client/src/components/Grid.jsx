import React from 'react';
import Card from './Card';
import './Grid.css';

const Grid = ({ catFacts }) => {
  return (
    <div className="grid-container">
      {catFacts.map((catFact, index) => (
        catFact && Object.keys(catFact).length > 0 && (
            <Card key={index} author={catFact.user} fact={catFact.fact} />
          )
      ))}
    </div>
  );
};

export default Grid;
