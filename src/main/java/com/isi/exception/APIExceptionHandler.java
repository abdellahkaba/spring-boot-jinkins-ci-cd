package com.isi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@ControllerAdvice
public class APIExceptionHandler {

    @ExceptionHandler(value = {RequestException.class})
    public ResponseEntity<APIException> handleRequestException(RequestException e) {
        APIException exception = new APIException(e.getMessage(), e.getStatus(), LocalDateTime.now());
        return new ResponseEntity<>(exception, e.getStatus());
    }


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException exp) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult().getAllErrors()
                .forEach(error -> {
                    var errorMessage = error.getDefaultMessage();
                    errors.add(errorMessage);
                });

        return ResponseEntity
                .status(BAD_REQUEST)
                .body(
                        ExceptionResponse.builder()
                                .validationErrors(errors)
                                .build()
                );
    }

    @ExceptionHandler(value = {EntityNotFoundException.class})
    public ResponseEntity<APIException> handleEntityNotFoundException(EntityNotFoundException e) {
        APIException exception = new APIException(e.getMessage(), HttpStatus.NOT_FOUND, LocalDateTime.now());
        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(value = {EntityExistsException.class})
    public ResponseEntity<APIException> handleEntityExistException(EntityExistsException e) {
        APIException exception = new APIException(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());
        return new ResponseEntity<>(exception, BAD_REQUEST);
    }

    @ExceptionHandler(value = {InvalidDateException.class})
    public ResponseEntity<APIException> handleEntityExistException(InvalidDateException e) {
        APIException exception = new APIException(e.getMessage(), HttpStatus.CONFLICT, LocalDateTime.now());
        return new ResponseEntity<>(exception, BAD_REQUEST);
    }

    @ExceptionHandler(value = {NumberFormatException.class})
    public ResponseEntity<APIException> handleNumberFormatException(NumberFormatException e) {
        APIException exception = new APIException(BAD_REQUEST.getReasonPhrase(), BAD_REQUEST, LocalDateTime.now());
        return new ResponseEntity<>(exception, BAD_REQUEST);
    }



}

