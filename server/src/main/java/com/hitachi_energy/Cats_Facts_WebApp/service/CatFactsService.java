package com.hitachi_energy.Cats_Facts_WebApp.service;

import com.hitachi_energy.Cats_Facts_WebApp.dto.CatFactDto;
import com.hitachi_energy.Cats_Facts_WebApp.fetchers.CatFactFetcher;
import com.hitachi_energy.Cats_Facts_WebApp.fetchers.UserFetcher;
import com.hitachi_energy.Cats_Facts_WebApp.mapper.CatFactMapper;
import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@AllArgsConstructor
public class CatFactsService implements ICatFactsService{

    private static final Logger logger = LoggerFactory.getLogger(CatFactsService.class);

    private final CatFactFetcher catFactFetcher;
    private final UserFetcher userFetcher;
    private final CatFactMapper catFactMapper;

    @Override
    public Flux<CatFactDto> fetchCatFacts() {
        return Flux.interval(Duration.ofSeconds(10))
                .flatMap(tick -> Mono.zip(
                        catFactFetcher.fetchRandomCatFact(),
                        userFetcher.fetchRandomUser()
                ))
                .map(tuple -> {
                    User user = tuple.getT2();
                    Fact fact = tuple.getT1();
                    logger.info("Fetched fact: '{}' by user: '{}'", fact.getDescription(), user.getName());
                    return catFactMapper.toCatFactDTO(user, fact);
                });
    }
}
