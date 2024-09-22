package com.hitachi_energy.Cats_Facts_WebApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/cat-facts")
                .allowedOrigins("http://localhost:5173")
                .allowedMethods("GET");
    }

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient catFactWebClient(WebClient.Builder builder) {
        return builder.baseUrl("https://cat-fact.herokuapp.com").build();
    }

    @Bean
    public WebClient userWebClient(WebClient.Builder builder) {
        return builder.baseUrl("https://randomuser.me").build();
    }
}
