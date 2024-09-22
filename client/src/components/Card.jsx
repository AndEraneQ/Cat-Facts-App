import React, { useEffect, useState } from 'react';
import styles from './Card.module.css';

const Card = ({ author, fact, isAnimating }) => {
    const [animate, setAnimate] = useState(false);
    const [currentAuthor, setCurrentAuthor] = useState(author);
    const [currentFact, setCurrentFact] = useState(fact);

    useEffect(() => {
        if (isAnimating) {
            setAnimate(true);
            const timer = setTimeout(() => {
                setCurrentAuthor(author);
                setCurrentFact(fact);
                setAnimate(false);
            }, 1000);

            return () => clearTimeout(timer);
        } else {
            setCurrentAuthor(author);
            setCurrentFact(fact);
        }
    }, [isAnimating, author, fact]);

    return (
        <div className={`${styles.cardContainer} ${animate ? styles.animate : ''}`}>
            <h4 className={`${styles.heading}`}>{currentAuthor}</h4>
            <p className={`${styles.description}`}>{currentFact}</p>
        </div>
    );
};

export default Card;
