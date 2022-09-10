package com.highload.chatic.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Неправильный запрос");
    }
}
