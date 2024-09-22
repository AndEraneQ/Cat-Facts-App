package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.dto.CatFactResponse;
import com.hitachi_energy.Cats_Facts_WebApp.mapper.CatFactResponseMapper;
import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import com.hitachi_energy.Cats_Facts_WebApp.utils.FactUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class CatFactFetcher implements ICatFactFetcher {
    private static final Logger logger = LoggerFactory.getLogger(CatFactFetcher.class);

    private final WebClient catFactWebClient;

    @Autowired
    public CatFactFetcher(@Qualifier("catFactWebClient") WebClient catFactWebClient) {
        this.catFactWebClient = catFactWebClient;
    }

    @Override
    public Mono<Fact> fetchRandomCatFact() {
        return catFactWebClient.get()
                .uri("/facts/random")
                .retrieve()
                .bodyToMono(CatFactResponse.class)
                .map(CatFactResponseMapper.INSTANCE::toFact)
                .doOnSuccess(fact -> logger.info("Fetched cat fact: {}", fact.getDescription()))
                .doOnError(e -> logger.error("Error occurred while fetching cat fact: {}", e.getMessage()))
                .onErrorReturn(new Fact(FactUtils.UNKNOWN_FACT));
    }
}
