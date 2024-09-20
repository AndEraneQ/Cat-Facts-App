package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import reactor.core.publisher.Mono;

public interface ICatFactFetcher {
    Mono<Fact> fetchRandomCatFact();
}
