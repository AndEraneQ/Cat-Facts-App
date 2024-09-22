package com.hitachi_energy.Cats_Facts_WebApp.utils;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserResponse;

public class UserUtils {
    public static final String UNKNOWN_USER = "Unknown User";

    public static String getFullName(UserResponse userResponse) {
        if (userResponse == null || userResponse.getResults() == null || userResponse.getResults().isEmpty()) {
            return UNKNOWN_USER;
        }
        UserResponse.Result result = userResponse.getResults().get(0);
        return result.getName().getFirst() + " " + result.getName().getLast();
    }
}
