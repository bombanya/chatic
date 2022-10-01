package com.highload.messageservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Ресурс не найден");
    }
}
