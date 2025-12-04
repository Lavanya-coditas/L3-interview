package com.coditas.movie.ticket.booking.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateScreenDto
{
    @NotBlank(message = "Screen name is mjst")
    private String name;

    @Positive(message = "rows must be positive")
    @NotNull(message = "total rows must be present")
    private Integer totalRows;

    @Positive(message = "seats must be positive")
    @NotNull(message = "seats must be present")
    private Integer seatsPerRow;

}
