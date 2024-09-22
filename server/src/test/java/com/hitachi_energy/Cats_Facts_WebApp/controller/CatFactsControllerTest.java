package com.hitachi_energy.Cats_Facts_WebApp.controller;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserCatFactDto;
import com.hitachi_energy.Cats_Facts_WebApp.service.CatFactsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@WebFluxTest(controllers = CatFactsController.class)
public class CatFactsControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CatFactsService catFactsService;

    private UserCatFactDto fact1;

    @BeforeEach
    public void setUp() {
        fact1 = new UserCatFactDto("User", "A cat fact");
    }

    @Test
    public void testGetCatFacts() {
        // Arrange
        when(catFactsService.fetchCatFacts()).thenReturn(Flux.just(fact1));

        // Act
        Flux<UserCatFactDto> result = webTestClient.get()
                .uri("/cat-facts")
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentTypeCompatibleWith(MediaType.TEXT_EVENT_STREAM)
                .returnResult(UserCatFactDto.class)
                .getResponseBody();

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(fact ->
                        fact.getUserName().equals("User") &&
                                fact.getFactDescription().equals("A cat fact")
                )
                .verifyComplete();
    }
}
