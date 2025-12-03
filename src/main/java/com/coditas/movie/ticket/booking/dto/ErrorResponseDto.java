package com.coditas.movie.ticket.booking.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponseDto< T >{
    private boolean success;
    private Integer errorCode;
    private String message;
    private T data;
}
