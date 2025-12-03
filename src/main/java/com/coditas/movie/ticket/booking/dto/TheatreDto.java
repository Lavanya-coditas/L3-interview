package com.coditas.movie.ticket.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class TheatreDto
{
    @NotBlank(message = "theatre name must be present")
    private  String name;

    @NotBlank(message = "city name must be present")
    private String city;

    @NotNull(message = "owner id must be present")
    private Long owner_id;
}
