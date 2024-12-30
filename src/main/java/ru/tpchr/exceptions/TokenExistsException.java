package ru.tpchr.exceptions;

public class TokenExistsException extends RuntimeException{
    private String message;

    public TokenExistsException(String message) {
        super(message);
    }
}
