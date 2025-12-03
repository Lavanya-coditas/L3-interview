package com.coditas.movie.ticket.booking.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScreenDto
{
    @NotBlank(message = "Screen name is mjst")
    private String name;

    @Positive(message = "rows must be positive")
    @NotNull(message = "total rows must be present")
    private Integer totalRows;

    @Positive(message = "seats must be postive")
    @NotNull(message = "setas must be present")
    private Integer seatsPerRow;

    @Positive(message = "Theatre id enteres must be positive")
    @NotNull(message = "Theatre id must be present")
    private Long theatreId;
}
