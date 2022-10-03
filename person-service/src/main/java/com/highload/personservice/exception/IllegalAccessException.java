package com.highload.personservice.exception;

public class IllegalAccessException extends RuntimeException {
    public IllegalAccessException() {
        super("Недостаточно прав доступа");
    }

}
