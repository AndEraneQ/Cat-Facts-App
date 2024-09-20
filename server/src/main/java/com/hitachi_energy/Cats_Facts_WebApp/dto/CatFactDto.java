package com.hitachi_energy.Cats_Facts_WebApp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CatFactDto {
    @JsonProperty("user")
    private String userName;

    @JsonProperty("fact")
    private String factDescription;
}
