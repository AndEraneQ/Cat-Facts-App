package com.hitachi_energy.Cats_Facts_WebApp.service;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserCatFactDto;
import com.hitachi_energy.Cats_Facts_WebApp.fetchers.CatFactFetcher;
import com.hitachi_energy.Cats_Facts_WebApp.fetchers.UserFetcher;
import com.hitachi_energy.Cats_Facts_WebApp.mapper.UserCatFactMapper;
import com.hitachi_energy.Cats_Facts_WebApp.models.Fact;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class CatFactsServiceTest {

    @Mock
    private CatFactFetcher catFactFetcher;

    @Mock
    private UserFetcher userFetcher;

    @InjectMocks
    private CatFactsService catFactsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchCatFacts_ShouldReturnUserCatFactDto() {
        // Arrange
        Fact fact = new Fact("Cats are great!");
        User user = new User("John Doe");
        when(catFactFetcher.fetchRandomCatFact()).thenReturn(Mono.just(fact));
        when(userFetcher.fetchRandomUser()).thenReturn(Mono.just(user));

        // Act
        Flux<UserCatFactDto> result = catFactsService.fetchCatFacts().take(1);

        // Assert
        StepVerifier.create(result)
                .expectNextMatches(userCatFactDto ->
                        userCatFactDto.getUserName().equals("John Doe") &&
                                userCatFactDto.getFactDescription().equals("Cats are great!")
                )
                .expectComplete()
                .verify();
    }
}
