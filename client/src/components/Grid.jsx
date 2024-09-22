import React from 'react';
import Card from './Card';
import styles from './Grid.module.css';

const Grid = ({ catFacts, animatedIndex }) => {
  return (
    <div className={`${styles.gridContainer}`}>
      {catFacts.map((catFact, index) => (
        catFact !== null && (
          <Card 
            key={index} 
            author={catFact.user} 
            fact={catFact.fact} 
            isAnimating={animatedIndex === index} 
          />
        )
      ))}
    </div>
  );
};

export default Grid;
