package com.highload.chatservice.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Неправильный запрос");
    }
}
