package com.coditas.movie.ticket.booking.services;


import com.coditas.movie.ticket.booking.constants.BookedSeatStatus;
import com.coditas.movie.ticket.booking.constants.TicketStatus;
import com.coditas.movie.ticket.booking.dto.TicketBookingRequestDto;
import com.coditas.movie.ticket.booking.dto.TicketResponseDto;
import com.coditas.movie.ticket.booking.entity.*;
import com.coditas.movie.ticket.booking.exceptions.ResourceNotFoundException;
import com.coditas.movie.ticket.booking.exceptions.SeatNotAvailableException;
import com.coditas.movie.ticket.booking.exceptions.UserNotFoundException;
import com.coditas.movie.ticket.booking.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TicketService {

    private final ShowRepository showRepository;
    private final TicketRepository ticketRepository;
    private final BookedSeatRepository bookedSeatRepository;
    private final SeatRepository seatRepository;
    private final UserRepository userRepository;


    public TicketResponseDto bookTicket(TicketBookingRequestDto requestDto, Long userId) {

        Show show = showRepository.findById(requestDto.getShowId()).orElseThrow(() -> new ResourceNotFoundException("Show not found"));

        Users user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found"));


        List<BookedSeat> alreadyBooked = bookedSeatRepository
                .findBySeatIdInAndStatus(requestDto.getSeatIds(), BookedSeatStatus.BOOKED);

        if (!alreadyBooked.isEmpty()) {
            throw new SeatNotAvailableException("One or more selected seats are already booked!");
        }

        List<Seat> seats = seatRepository.findAllById(requestDto.getSeatIds());
        double totalCost = seats.size() * show.getPrice();

        Ticket ticket = new Ticket();
        ticket.setBookingTime(LocalDateTime.now());
        ticket.setStatus(TicketStatus.BOOKED);
        ticket.setBookedShow(show);
        ticket.setUser(user);
        ticket.setTotalPrice(totalCost);


        Ticket savedTicket = ticketRepository.save(ticket);
        List<BookedSeat> bookedSeatsList = new ArrayList<>();
        for (Seat seat : seats) {
            BookedSeat bookedSeat = new BookedSeat();
            bookedSeat.setBookedShow(show);
            bookedSeat.setSeat(seat);
            bookedSeat.setTicket(savedTicket);
            bookedSeat.setStatus(BookedSeatStatus.BOOKED);
            bookedSeatsList.add(bookedSeat);
        }
        bookedSeatRepository.saveAll(bookedSeatsList);

        TicketResponseDto ticketResponseDto = new TicketResponseDto(
                savedTicket.getId(),
                savedTicket.getBookingTime().toString(),
                totalCost,
                savedTicket.getStatus().toString(),

                show.getStartTime().toString(),
                seats.stream()
                        .map(seat -> "R" + seat.getRowNumber() + "S" + seat.getSeatNumber())
                        .toList()
        );
        return ticketResponseDto;
    }

}