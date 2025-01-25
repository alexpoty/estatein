package com.alexpoty.estatein.property.exception;

public class PropertyAlreadyExistsException extends RuntimeException {
    public PropertyAlreadyExistsException(String message) {
        super(message);
    }
}
