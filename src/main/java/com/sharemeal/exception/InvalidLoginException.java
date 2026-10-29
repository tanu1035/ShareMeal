package com.sharemeal.exception;

public class InvalidLoginException extends ShareMealException {
    public InvalidLoginException() {
        super("Invalid email or password.");
    }

    public InvalidLoginException(String message) {
        super(message);
    }
}
