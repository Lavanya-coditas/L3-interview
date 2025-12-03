package com.coditas.movie.ticket.booking.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTheatreDto {
    @NotBlank(message = "Theatre name must be present")
    private String name;

    @NotBlank(message = "City name must be present")
    private String city;
}