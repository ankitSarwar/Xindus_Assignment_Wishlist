package com.example.Xindus_Assignment_Ankit_Sarwar.exception;

public class ErrorResponse extends Throwable {
    private final String message;

    public ErrorResponse(String message, Exception e) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
