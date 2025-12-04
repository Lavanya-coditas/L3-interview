package com.coditas.movie.ticket.booking.controllers;

import com.coditas.movie.ticket.booking.dto.ApiResponseDto;
import com.coditas.movie.ticket.booking.dto.TicketBookingRequestDto;
import com.coditas.movie.ticket.booking.dto.TicketResponseDto;
import com.coditas.movie.ticket.booking.services.TicketService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService ticketService;

    @PostMapping("/book/{userId}")
    @PreAuthorize("hasAuthority('USER','THEATRE_OWNER')")
    public ApiResponseDto<TicketResponseDto> bookTicket(@PathVariable Long userId, @RequestBody TicketBookingRequestDto requestDto) {
        TicketResponseDto response = ticketService.bookTicket(requestDto, userId);
        return new ApiResponseDto<>("Ticket booked succeffulyy", true,response);
    }

    @GetMapping("/tickets/{userId}")
    @PreAuthorize("hasAuthority('USER','THEATRE_OWNER')")
    public ApiResponseDto<List<TicketResponseDto>> getUserTickets(@PathVariable Long userId) {
        List<TicketResponseDto> tickets = ticketService.getTicketsByUser(userId);
        return new ApiResponseDto<>("Ticket fetched succeffulyy", true,tickets);
    }

    @DeleteMapping("/tickets/{userId}/{ticketId}")
    @PreAuthorize("hasAuthority('USER','THEATRE_OWNER')")
    public ApiResponseDto<TicketResponseDto> cancelTicket(@PathVariable Long userId, @PathVariable Long ticketId) {
        TicketResponseDto response = ticketService.cancelTicket(ticketId, userId);
        return new ApiResponseDto<>("Ticket Cancelled succesfully ",true,response);
    }


}
