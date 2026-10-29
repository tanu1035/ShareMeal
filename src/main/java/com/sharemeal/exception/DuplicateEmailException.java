package com.sharemeal.exception;

public class DuplicateEmailException extends ShareMealException {
    public DuplicateEmailException(String email) {
        super("An account with email '" + email + "' already exists.");
    }
}
