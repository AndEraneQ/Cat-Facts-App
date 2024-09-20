package com.hitachi_energy.Cats_Facts_WebApp.fetchers;

import com.hitachi_energy.Cats_Facts_WebApp.models.User;
import reactor.core.publisher.Mono;

public interface IUserFetcher {
    Mono<User> fetchRandomUser();
}
