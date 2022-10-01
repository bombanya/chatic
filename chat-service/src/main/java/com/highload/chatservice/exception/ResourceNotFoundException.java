package com.highload.chatservice.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException() {
        super("Ресурс не найден");
    }
}
