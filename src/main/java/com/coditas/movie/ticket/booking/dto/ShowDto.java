package com.coditas.movie.ticket.booking.dto;

import com.coditas.movie.ticket.booking.entity.Movie;
import com.coditas.movie.ticket.booking.entity.Screen;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShowDto
{
//    private LocalDateTime startTime= LocalDateTime.now();
//
//    private Instant endTime = Instant.now().plus(7, ChronoUnit.DAYS);

    private Double price;

    private Movie movieId;

    private Long screenId;


}
