package com.coditas.movie.ticket.booking.repositories;

import com.coditas.movie.ticket.booking.entity.Ticket;
import com.coditas.movie.ticket.booking.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {
    List<Ticket> findByUser(Users user);
}