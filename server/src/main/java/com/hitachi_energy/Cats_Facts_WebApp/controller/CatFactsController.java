package com.hitachi_energy.Cats_Facts_WebApp.controller;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserCatFactDto;
import com.hitachi_energy.Cats_Facts_WebApp.service.CatFactsService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class CatFactsController {

    private final CatFactsService catFactsService;

    @GetMapping(value = "/cat-facts", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserCatFactDto> getCatFacts() {
        return catFactsService.fetchCatFacts();
    }
}
