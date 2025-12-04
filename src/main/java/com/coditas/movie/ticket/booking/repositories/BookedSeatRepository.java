package com.coditas.movie.ticket.booking.repositories;

import com.coditas.movie.ticket.booking.constants.BookedSeatStatus;
import com.coditas.movie.ticket.booking.entity.BookedSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookedSeatRepository extends JpaRepository<BookedSeat, Long> {

    List<BookedSeat> findBySeatIdInAndStatus(List<Long> seatIds, BookedSeatStatus status);
}