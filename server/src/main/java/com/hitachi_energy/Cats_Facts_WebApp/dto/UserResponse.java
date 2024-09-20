package com.hitachi_energy.Cats_Facts_WebApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private List<Result> results;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result {
        private Name name;

        @Data
        @AllArgsConstructor
        @NoArgsConstructor
        public static class Name {
            private String first;
            private String last;
        }
    }
}
