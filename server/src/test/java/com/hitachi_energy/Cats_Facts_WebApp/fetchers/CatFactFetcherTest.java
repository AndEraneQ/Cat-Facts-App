package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.dto.CatFactResponse;
import com.hitachi_energy.Cats_Facts_WebApp.mapper.CatFactResponseMapper;
import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import com.hitachi_energy.Cats_Facts_WebApp.utils.FactUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CatFactFetcherTest {

    @Mock
    private WebClient.Builder webClientBuilder;

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestHeadersUriSpec requestHeadersUriSpec;

    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    @InjectMocks
    private CatFactFetcher catFactFetcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        catFactFetcher = CatFactFetcher.create(webClientBuilder);
    }

    @Test
    void fetchRandomCatFact_Success() {
        // Given
        CatFactResponse catFactResponse = new CatFactResponse();
        catFactResponse.setText("Cats can fall from great heights.");
        Fact expectedFact = CatFactResponseMapper.INSTANCE.toFact(catFactResponse);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CatFactResponse.class)).thenReturn(Mono.just(catFactResponse));

        // When
        Mono<Fact> resultMono = catFactFetcher.fetchRandomCatFact();

        // Then
        Fact resultFact = resultMono.block();
        assertEquals(expectedFact.getDescription(), resultFact.getDescription());
    }

    @Test
    void fetchRandomCatFact_Error() {
        // Given
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(CatFactResponse.class)).thenReturn(Mono.error(new WebClientResponseException(
                "Error while fetching the fact", 404, "Not Found", null, null, null)));

        // When
        Mono<Fact> resultMono = catFactFetcher.fetchRandomCatFact();

        // Then
        Fact resultFact = resultMono.block();
        assertEquals(FactUtils.UNKNOWN_FACT, resultFact.getDescription());
    }
}
