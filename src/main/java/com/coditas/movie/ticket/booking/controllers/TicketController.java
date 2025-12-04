package com.coditas.movie.ticket.booking.controllers;

import com.coditas.movie.ticket.booking.dto.TicketBookingRequestDto;
import com.coditas.movie.ticket.booking.dto.TicketResponseDto;
import com.coditas.movie.ticket.booking.services.TicketService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/book/{userId}")
    @PreAuthorize("hasAuthority('USER','THEATRE_OWNER')")
    public ResponseEntity<TicketResponseDto> bookTicket(@PathVariable Long userId, @RequestBody TicketBookingRequestDto requestDto) {
        TicketResponseDto response = ticketService.bookTicket(requestDto, userId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
