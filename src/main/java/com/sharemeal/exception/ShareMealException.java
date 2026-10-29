package com.sharemeal.exception;

/** Base class for all application-specific (business) errors. */
public class ShareMealException extends RuntimeException {
    public ShareMealException(String message) {
        super(message);
    }
}
