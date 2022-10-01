package com.highload.messageservice.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Неправильный запрос");
    }
}
