package com.highload.personservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Ресурс не найден");
    }
}
