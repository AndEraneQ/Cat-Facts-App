import { fromEvent, of } from 'rxjs';
import { map, catchError } from 'rxjs/operators';

class CatFactsService {
    constructor(url) {
        this.url = url;
        this.eventSource = null;
    }

    initialize() {
        this.eventSource = new EventSource(this.url);
        
        this.eventSource.onerror = (error) => {
            console.error('EventSource failed:', error);
        };

        return fromEvent(this.eventSource, 'message').pipe(
            map(event => {
                const data = JSON.parse(event.data);
                console.log('Received data from server:', data);
                return { user: data.user, fact: data.fact };
            }),
            catchError(error => {
                console.error('Error while receiving data:', error);
                return of(null);
            })
        );
    }

    close() {
        if (this.eventSource) {
            this.eventSource.close();
            console.log('EventSource closed');
        }
    }
}

export default CatFactsService;
