package ru.pracricum.ewmservice.exception;

public class ConflictingRequestException extends RuntimeException {

    public ConflictingRequestException(String message) {
        super(message);
    }
}
