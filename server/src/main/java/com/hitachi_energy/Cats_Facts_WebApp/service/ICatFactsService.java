package com.hitachi_energy.Cats_Facts_WebApp.service;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserCatFactDto;
import reactor.core.publisher.Flux;

public interface ICatFactsService {
    Flux<UserCatFactDto> fetchCatFacts();
}
