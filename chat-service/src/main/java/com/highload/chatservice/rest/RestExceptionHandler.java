package com.highload.chatservice.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.highload.chatservice.exception.IllegalAccessException;
import com.highload.chatservice.exception.InvalidRequestException;
import com.highload.chatservice.exception.ResourceNotFoundException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(IllegalAccessException.class)
    public ResponseEntity<Object> handleIllegalAccessException(IllegalAccessException ex, WebRequest request) {
        var responseBody = new ErrorResponseBody(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(responseBody, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<Object> handleInvalidRequestException(InvalidRequestException ex, WebRequest request) {
        var responseBody = new ErrorResponseBody(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
        var responseBody = new ErrorResponseBody(ex.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> handleDataAccessException(WebRequest request) {
        var responseBody = new ErrorResponseBody("Неправильный запрос",
                request.getDescription(false));
        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
                                                                         HttpHeaders headers,
                                                                         HttpStatus status,
                                                                         WebRequest request) {
        var responseBody = new ErrorResponseBody("Метод не поддерживается",
                request.getDescription(false));
        return new ResponseEntity<>(responseBody, status);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
                                                                     HttpHeaders headers,
                                                                     HttpStatus status,
                                                                     WebRequest request) {
        var responseBody = new ErrorResponseBody("Данный формат не поддерживается",
                request.getDescription(false));
        return new ResponseEntity<>(responseBody, status);
    }


    //exception from @Valid
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        var responseBody = new ErrorResponseBody("Неправильный запрос",
                request.getDescription(false),
                ex.getFieldErrors());
        return new ResponseEntity<>(responseBody, status);
    }

    @JsonInclude(Include.NON_NULL)
    private record ErrorResponseBody(
            Instant timestamp,
            String message,
            String path,
            Map<String, String> fieldValidationErrors
    ) {
        public ErrorResponseBody(String message, String path) {
            this(Instant.now(), message, path, null);
        }

        private ErrorResponseBody(String message, String path, List<FieldError> fieldErrors) {
            this(Instant.now(), message, path, fieldErrors
                    .stream()
                    .collect(Collectors.toMap(FieldError::getField,
                            FieldError::getDefaultMessage)));
        }
    }
}

