package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserResponse;
import com.hitachi_energy.Cats_Facts_WebApp.mapper.UserResponseMapper;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import com.hitachi_energy.Cats_Facts_WebApp.utils.UserUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class UserFetcher implements IUserFetcher {

    private static final Logger logger = LoggerFactory.getLogger(UserFetcher.class);

    private final WebClient webClient;

    public UserFetcher(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://randomuser.me").build();
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
                .onErrorReturn(new User(UserUtils.UNKNOWN_USER));
    }
}
