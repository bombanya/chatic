package com.highload.messageservice.exception;

public class IllegalAccessException extends RuntimeException {
    public IllegalAccessException() {
        super("Недостаточно прав доступа");
    }

}
