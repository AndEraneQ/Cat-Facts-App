import React from 'react';
import './Card.css';

const Card = ({ author, fact }) => {
  return (
    <div className="card">
      <h3>by {author}</h3>
      <p>{fact}</p>
    </div>
  );
};

export default Card;
