package ru.pracricum.ewmservice.exception;

public class NotExistObjectException extends RuntimeException {
    public NotExistObjectException(String message) {
        super(message);
    }
}
