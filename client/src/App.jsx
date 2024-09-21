import React, { useEffect, useState } from 'react';
import { fromEvent, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import Grid from './components/Grid';
import Header from './components/Header';

const App = () => {
    const [catFacts, setCatFacts] = useState(Array(6).fill(null));

    let index = 0;

    useEffect(() => {
        const eventSource = new EventSource('http://localhost:8080/cat-facts');
        
        const catFacts$ = fromEvent(eventSource, 'message').pipe(
            map(event => {
                const data = JSON.parse(event.data);
                console.log('Otrzymane dane z serwera:', data);
                return { user: data.user, fact: data.fact };
            }),
            catchError(error => {
                console.error('Błąd podczas odbierania danych:', error);
                return of(null);
            })
        );

        const subscription = catFacts$.subscribe(newData => {
            if (newData) {
                setCatFacts(prevCatFacts => {
                    const updatedCatFacts = [...prevCatFacts];
                    updatedCatFacts[index] = newData;
                    index = (index+1)%6
                    return updatedCatFacts;
                });
            }
        });

        return () => {
            subscription.unsubscribe();
            eventSource.close();
        };
    }, []);

    return (
        <div>
            <Header/>
            <Grid catFacts={catFacts} />
        </div>
    );
};

export default App;
