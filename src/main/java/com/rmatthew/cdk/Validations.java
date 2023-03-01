package com.rmatthew.cdk;

public class Validations {

    private Validations() {}

    public static void validateNotEmpty(String s, String message) {
        if (s == null || s.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
