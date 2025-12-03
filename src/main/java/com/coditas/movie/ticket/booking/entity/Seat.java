package com.coditas.movie.ticket.booking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seat_table")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rowNumber;

    private Integer seatNumber;


    private String seatName;

    @ManyToOne
    @JoinColumn(name = "screen_id")
    private Screen screen;

    //seat can be booked for diffrent shows
    @OneToMany(mappedBy = "seat")
    private List<BookedSeat> bookedSeats;

}
