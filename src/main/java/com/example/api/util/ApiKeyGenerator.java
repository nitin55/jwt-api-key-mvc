package com.example.api.util;

import java.util.UUID;

public class ApiKeyGenerator {

    /**
     * Generates a random API key in UUID format.
     * Example: 123e4567-e89b-12d3-a456-426614174000
     *
     * @return String API key
     */
    public static String generate() {
        return UUID.randomUUID().toString();
    }

    public static void main(String[] args) {
        // Example usage
        String apiKey = ApiKeyGenerator.generate();
        System.out.println("Generated API Key: " + apiKey);
    }
}

