package com.hitachi_energy.Cats_Facts_WebApp.service;

import com.hitachi_energy.Cats_Facts_WebApp.dto.CatFactDto;
import reactor.core.publisher.Flux;

public interface ICatFactsService {
    Flux<CatFactDto> fetchCatFacts();
}
