package com.coditas.movie.ticket.booking.exceptions;


import com.coditas.movie.ticket.booking.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDto<String>> handleUserNotFoundException(UserNotFoundException ex) {
        log.error("User not found: {}", ex.getMessage());
        ErrorResponseDto error = new ErrorResponseDto(
                false,
                HttpStatus.NOT_FOUND.value(),
                "USER_NOT_FOUND",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        log.error("Resource not found: {}", ex.getMessage());
        ErrorResponseDto error = new ErrorResponseDto(
                false,
                HttpStatus.NOT_FOUND.value(),
                "RESOURCE_NOT_FOUND",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public ResponseEntity<ErrorResponseDto<String>> handleUnauthorizedAccessException(UnauthorizedAccessException ex) {
        log.error("Unauthorized access: {}", ex.getMessage());
        ErrorResponseDto error = new ErrorResponseDto(
                false,
                HttpStatus.FORBIDDEN.value(),
                "UNAUTHORISED ACCESS EXCEPTION OCCURED",
                ex.getMessage()

        );
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        log.error("Invalid credentials: {}", ex.getMessage());
        ErrorResponseDto error = new ErrorResponseDto(
                false,
                HttpStatus.UNAUTHORIZED.value(),
                "INVALID_CREDENTIALS",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto<String >> handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Invalid argument: {}", ex.getMessage());
        ErrorResponseDto error = new ErrorResponseDto(
                false,
                HttpStatus.BAD_REQUEST.value(),
                "INVALID_INPUT",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto<String>> userAlreadyExistException(UserAlreadyExistsException ex) {
        log.error("user ALready exists exception occured: {}", ex.getMessage());
        ErrorResponseDto error = new ErrorResponseDto(
                false,
                HttpStatus.BAD_REQUEST.value(),
                "USER ALREADY EXISTS",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto<String>> handleHttpRequestMethodNotSupported(
            HttpRequestMethodNotSupportedException ex, WebRequest request) {

        log.error("Http method not allowed :{}",ex.getMessage());
        ErrorResponseDto errorResponseDto= new ErrorResponseDto<>(false,
                HttpStatus.METHOD_NOT_ALLOWED.value(),
              HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(),
               "HTTP method '" + ex.getMethod() + "' is not supported for this endpoint. Supported methods: "
                );
        return new ResponseEntity<>(errorResponseDto,HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(InvalidShowTimingException.class)
    public ResponseEntity<ErrorResponseDto<String>> handleShowTimingException(InvalidShowTimingException ex) {
        log.error("Invalid show timing: {}", ex.getMessage());

        ErrorResponseDto<String> error = new ErrorResponseDto<>(false,
                HttpStatus.BAD_REQUEST.value(),
                "Invalid show timing entered",
                ex.getMessage()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    }

