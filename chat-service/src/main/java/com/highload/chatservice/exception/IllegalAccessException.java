package com.highload.chatservice.exception;

public class IllegalAccessException extends RuntimeException {
    public IllegalAccessException() {
        super("Недостаточно прав доступа");
    }

}
