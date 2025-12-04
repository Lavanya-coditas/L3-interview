package com.coditas.movie.ticket.booking.dto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketResponseDto {

    private Long ticketId;
    private String bookingTime;
    private Double totalPrice;
    private String status;
    private String showTime;
    private List<String> seats;
}
