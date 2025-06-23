package com.isi.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class APIExceptionHandlerTest {

    private APIExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new APIExceptionHandler();
    }

    @Test
    void handleRequestException_shouldReturnExpectedResponse() {
        RequestException exception = new RequestException("Invalid request", HttpStatus.BAD_REQUEST);
        ResponseEntity<APIException> response = handler.handleRequestException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request", response.getBody().getMessage());
    }

    @Test
    void handleEntityNotFoundException_shouldReturnNotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Not found");
        ResponseEntity<APIException> response = handler.handleEntityNotFoundException(exception);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Not found", response.getBody().getMessage());
    }

    @Test
    void handleEntityExistsException_shouldReturnBadRequest() {
        EntityExistsException exception = new EntityExistsException("Already exists");
        ResponseEntity<APIException> response = handler.handleEntityExistException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Already exists", response.getBody().getMessage());
    }

    @Test
    void handleInvalidDateException_shouldReturnBadRequest() {
        InvalidDateException exception = new InvalidDateException("Date not valid");
        ResponseEntity<APIException> response = handler.handleEntityExistException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Date not valid", response.getBody().getMessage());
    }

    @Test
    void handleNumberFormatException_shouldReturnBadRequest() {
        NumberFormatException exception = new NumberFormatException("Invalid number");
        ResponseEntity<APIException> response = handler.handleNumberFormatException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Bad Request", response.getBody().getMessage()); // BAD_REQUEST.getReasonPhrase()
    }

    @Test
    void handleValidationExceptions_shouldReturnValidationErrors() {
        // Mock des erreurs de validation
        ObjectError error1 = new ObjectError("field1", "must not be null");
        ObjectError error2 = new ObjectError("field2", "must not be empty");

        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getAllErrors()).thenReturn(List.of(error1, error2));

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ExceptionResponse> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody().getValidationErrors().contains("must not be null"));
        assertTrue(response.getBody().getValidationErrors().contains("must not be empty"));
    }
}
