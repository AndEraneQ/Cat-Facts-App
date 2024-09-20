package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.dto.CatFactResponse;
import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class CatFactFetcher implements ICatFactFetcher {
    private static final Logger logger = LoggerFactory.getLogger(CatFactFetcher.class);

    private final WebClient webClient;

    public CatFactFetcher(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://cat-fact.herokuapp.com").build();
    }

    @Override
    public Mono<Fact> fetchRandomCatFact() {
        return webClient.get()
                .uri("/facts/random")
                .retrieve()
                .bodyToMono(CatFactResponse.class)
                .map(catFactResponse -> new Fact(catFactResponse.getText()))
                .onErrorResume(e -> {
                        logger.error("Failed to fetch cat fact: {}", e.getMessage());
                        return Mono.just(new Fact("Unknown fact"));
                    }
                );
    }
}
