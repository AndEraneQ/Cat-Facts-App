import React, { useEffect, useState } from 'react';
import Grid from './components/Grid';
import Header from './components/Header';
import Loading from './components/Loading';
import styles from './App.module.css';
import CatFactsService from './service/CatFactsService';

const API_URL = 'http://localhost:8080/cat-facts';

const App = () => {
    const [catFacts, setCatFacts] = useState(Array(6).fill(null));
    const [animatedIndex, setAnimatedIndex] = useState(null);
    const catFactsService = new CatFactsService(API_URL);
    const hasValidFacts = catFacts.some(catFact => catFact !== null);
    let index = 0;

    useEffect(() => {
        const catFacts$ = catFactsService.initialize();
        const subscription = catFacts$.subscribe(newData => {
            if (newData) {
                setCatFacts(prevCatFacts => {
                    const updatedCatFacts = [...prevCatFacts];
                    updatedCatFacts[index] = newData;
                    return updatedCatFacts;
                });

                setAnimatedIndex(index);
                index = (index + 1) % 6;
            }
        });

        return () => {
            subscription.unsubscribe();
            catFactsService.close();
        };
    }, []);

    return (
        <div className={`${styles.appContainer}`}>
            <Header />
            {hasValidFacts ? <Grid catFacts={catFacts} animatedIndex={animatedIndex} /> : <Loading />}
        </div>
    );
};

export default App;
