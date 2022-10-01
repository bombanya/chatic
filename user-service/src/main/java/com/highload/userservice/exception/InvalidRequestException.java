package com.highload.userservice.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Неправильный запрос");
    }
}
