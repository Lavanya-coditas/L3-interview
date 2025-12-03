
package com.coditas.movie.ticket.booking.entity;

import com.coditas.movie.ticket.booking.constants.TicketStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "ticket_table")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime bookingTime;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private TicketStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Users user;

    @ManyToOne
    @JoinColumn(name = "show_id")
    private Show bookedShow;

    //multiple seat for a ticket
    @OneToMany(mappedBy = "ticket")
    private List<BookedSeat> bookedSeats;


}


