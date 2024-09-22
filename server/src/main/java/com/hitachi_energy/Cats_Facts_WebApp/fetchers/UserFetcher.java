package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserResponse;
import com.hitachi_energy.Cats_Facts_WebApp.mapper.UserResponseMapper;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import com.hitachi_energy.Cats_Facts_WebApp.utils.UserUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class UserFetcher implements IUserFetcher {

    private static final Logger logger = LoggerFactory.getLogger(UserFetcher.class);

    private final WebClient webClient;

    public static UserFetcher create(WebClient.Builder webClientBuilder) {
        return new UserFetcher(webClientBuilder.baseUrl("https://randomuser.me").build());
    }

    @Override
    public Mono<User> fetchRandomUser() {
        return webClient.get()
                .uri("/api")
                .retrieve()
                .bodyToMono(UserResponse.class)
                .map(UserResponseMapper.INSTANCE::toUser)
                .doOnSuccess(user -> logger.info("Fetched user: {}", user.getName()))
                .doOnError(e -> logger.error("Error fetching user: {}", e.getMessage()))
                .onErrorResume(e -> {
                    logger.error("Returning UNKNOWN_USER due to fetch error", e);
                    return Mono.just(new User(UserUtils.UNKNOWN_USER));
                });
    }
}
