package com.coditas.movie.ticket.booking.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ShowResponseDto {
    private Long showId;
    private String movieName;
    private String theatreName;
    private String screenName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Double price;
}
