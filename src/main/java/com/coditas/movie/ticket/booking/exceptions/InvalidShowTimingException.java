package com.coditas.movie.ticket.booking.exceptions;

public class InvalidShowTimingException extends RuntimeException {
    public InvalidShowTimingException(String message) {
        super(message);
    }
}
