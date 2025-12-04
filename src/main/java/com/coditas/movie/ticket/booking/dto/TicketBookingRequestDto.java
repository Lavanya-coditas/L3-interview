package com.coditas.movie.ticket.booking.dto;


import lombok.Data;

import java.util.List;

@Data
public class TicketBookingRequestDto {

    private Long showId;
    private List<Long> seatIds;
}
