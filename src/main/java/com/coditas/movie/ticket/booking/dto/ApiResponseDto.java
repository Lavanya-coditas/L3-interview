package com.coditas.movie.ticket.booking.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDto<T>
{
    private String message;
    private boolean success;
    private T data;
}