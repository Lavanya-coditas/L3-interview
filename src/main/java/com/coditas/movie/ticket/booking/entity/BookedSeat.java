package com.coditas.movie.ticket.booking.entity;

    import com.coditas.movie.ticket.booking.constants.BookedSeatStatus;
    import jakarta.persistence.*;
    import lombok.*;

    @Entity
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Table(name = "booked_seat_table")
    public class BookedSeat {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @ManyToOne
        @JoinColumn(name = "show_id", nullable = false)
        private Show bookedShow;


        @ManyToOne
        @JoinColumn(name = "seat_id", nullable = false)
        private Seat seat;


        @ManyToOne
        @JoinColumn(name = "ticket_id")
        private Ticket ticket;


        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        private BookedSeatStatus status;
    }

