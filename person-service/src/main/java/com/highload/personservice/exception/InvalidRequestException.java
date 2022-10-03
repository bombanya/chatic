package com.highload.personservice.exception;

public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException() {
        super("Неправильный запрос");
    }
}
