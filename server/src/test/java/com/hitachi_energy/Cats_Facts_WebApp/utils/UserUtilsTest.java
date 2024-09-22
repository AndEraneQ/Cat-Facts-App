package com.hitachi_energy.Cats_Facts_WebApp.utils;

import com.hitachi_energy.Cats_Facts_WebApp.dto.UserResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserUtilsTest {

    @Test
    void getFullName_NullUserResponse_ReturnsUnknownUser() {
        // Given
        UserResponse userResponse = null;

        // When
        String fullName = UserUtils.getFullName(userResponse);

        // Then
        assertEquals(UserUtils.UNKNOWN_USER, fullName);
    }

    @Test
    void getFullName_EmptyResults_ReturnsUnknownUser() {
        // Given
        UserResponse userResponse = new UserResponse();
        userResponse.setResults(List.of());

        // When
        String fullName = UserUtils.getFullName(userResponse);

        // Then
        assertEquals(UserUtils.UNKNOWN_USER, fullName);
    }

    @Test
    void getFullName_ValidUserResponse_ReturnsFullName() {
        // Given
        UserResponse userResponse = new UserResponse();
        UserResponse.Result result = new UserResponse.Result();
        UserResponse.Result.Name name = new UserResponse.Result.Name();
        name.setFirst("John");
        name.setLast("Doe");
        result.setName(name);
        userResponse.setResults(List.of(result));

        // When
        String fullName = UserUtils.getFullName(userResponse);

        // Then
        assertEquals("John Doe", fullName);
    }
}
