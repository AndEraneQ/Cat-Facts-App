package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserResponse;
import com.hitachi_energy.Cats_Facts_WebApp.models.User;
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
                .flatMap(this::processUserResponse)
                .onErrorResume(e -> {
                    logger.error("Failed to fetch user: {}", e.getMessage());
                    return Mono.just(new User("Unknown User"));
                });
    }

    private Mono<User> processUserResponse(UserResponse response) {
        List<UserResponse.Result> results = response.getResults();
        if (results.isEmpty()) {
            logger.warn("No users found in response");
            return Mono.just(new User("Unknown User"));
        }
        UserResponse.Result.Name name = results.get(0).getName();
        String fullName = createFullUserName(name.getFirst(), name.getLast());
        return Mono.just(new User(fullName));
    }

    private String createFullUserName(String firstName, String lastName) {
        return firstName + " " + lastName;
    }
}
