package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserResponse;
import com.hitachi_energy.Cats_Facts_WebApp.mapper.UserResponseMapper;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import com.hitachi_energy.Cats_Facts_WebApp.utils.UserUtils;
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

class UserFetcherTest {

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
    private UserFetcher userFetcher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);
        when(webClientBuilder.build()).thenReturn(webClient);
        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        userFetcher = UserFetcher.create(webClientBuilder);
    }

    @Test
    void fetchRandomUser_Success() {
        // Given
        UserResponse userResponse = new UserResponse();
        User expectedUser = UserResponseMapper.INSTANCE.toUser(userResponse);

        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponse.class)).thenReturn(Mono.just(userResponse));

        // When
        Mono<User> resultMono = userFetcher.fetchRandomUser();

        // Then
        User resultUser = resultMono.block();
        assertEquals(expectedUser.getName(), resultUser.getName());
    }

    @Test
    void fetchRandomUser_Error() {
        // Given
        when(requestHeadersUriSpec.uri(anyString())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(UserResponse.class)).thenReturn(Mono.error(new WebClientResponseException(
                "User not found", 404, "Not Found", null, null, null)));

        // When
        Mono<User> resultMono = userFetcher.fetchRandomUser();

        // Then
        User resultUser = resultMono.block();
        assertEquals(UserUtils.UNKNOWN_USER, resultUser.getName());
    }
}
