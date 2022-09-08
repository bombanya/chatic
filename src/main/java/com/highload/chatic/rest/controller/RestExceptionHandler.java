package com.highload.chatic.rest.controller;

import com.highload.chatic.exception.IllegalAccessException;
import com.highload.chatic.exception.InvalidRequestException;
import com.highload.chatic.exception.ResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.NonNullApi;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleExceptionInternal(
            Exception ex, Object body, HttpHeaders headers,
            HttpStatus status, WebRequest request
    ) {
        ErrorResponseEntity responseBody = switch (ex) {
            case ResourceNotFoundException e:
                yield new ErrorResponseEntity(request.getLocale(), "Ресурс не найден", HttpStatus.NOT_FOUND, request.getContextPath());
            case IllegalAccessException e:
                yield new ErrorResponseEntity(request.getLocale(), "Недостаточно прав доступа", HttpStatus.FORBIDDEN, request.getContextPath());
            case InvalidRequestException e:
                yield new ErrorResponseEntity(request.getLocale(), "Неправильный запрос", HttpStatus.BAD_REQUEST, request.getContextPath());
            default:
                yield new ErrorResponseEntity(request.getLocale(), "Внутренняя ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR, request.getContextPath());
        };
        return new ResponseEntity<>(responseBody, responseBody.status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var responseBody = new ErrorResponseEntity(request.getLocale(), "Метод не поддерживается", HttpStatus.BAD_REQUEST, request.getContextPath());
        return new ResponseEntity<>(responseBody, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var responseBody = new ErrorResponseEntity(request.getLocale(), "Данный формат не поддерживается", HttpStatus.BAD_REQUEST, request.getContextPath());
        return new ResponseEntity<>(responseBody, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        var responseBody = new ErrorResponseEntity(request.getLocale(), "Неправильный запрос", HttpStatus.BAD_REQUEST, request.getContextPath());
        return new ResponseEntity<>(responseBody, status);
    }

    public record ErrorResponseEntity(
            String timestamp,
            HttpStatus status,
            String error,
            String message,
            String path
    ) {
        public ErrorResponseEntity(Locale locale, String message, HttpStatus status, String path) {
            this(getTimeStamp(locale), status, status.name(), message, path);
        }

        private static String getTimeStamp(Locale locale) {
            var formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME.localizedBy(locale);
            return formatter.format(Instant.now());
        }
    }
}
